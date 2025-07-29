<template>
  <div class="invoice-wrapper">
    <div class="title">{{ t('sale_order.title') }}</div>
    <div class="qr-code">
      <img :src="qrCodeUrl" alt="QR Code" width="150" height="150" />
    </div>
    <div class="info">
      <strong>Mã hóa đơn:</strong> {{ props.saleOrderId }}<br />
    </div>
    <q-separator color="dark" size="2px" />

    <div class="info">

      <strong >Đơn vị bán hàng:</strong> {{ business.name }}<br
        />
      <strong>Mã số thế:</strong> {{
        business.mst }}<br />
      <strong>Địa chỉ:</strong> {{ business.address
      }}<br/>
      <strong>Điện thoại:</strong> {{ business.phone }}<br />
      <strong>Tên Ngân hàng :</strong> {{ business.bankName }}<br />
      <strong>Tài khoản Ngân hàng :</strong> {{ business.bankAccount }}<br />
    </div>
    <q-separator color="dark" size="2px" />
    <div class="info">
      <strong >Họ tên người mua hàng:</strong> {{ customer.name }}<br
        />
      <strong v-if="customer.customerType == 'companyCustomer'">{{ t('customer.company_name') }}:</strong> {{
        customer.companyName }}<br v-if="customer.customerType == 'companyCustomer'" />
      <strong v-if="customer.customerType == 'companyCustomer'">{{ t('customer.tax_code') }}:</strong> {{ customer.mst
      }}<br v-if="customer.customerType == 'companyCustomer'" />
      <strong>{{ t('customer.phone') }}:</strong> {{ customer.phone }}<br />
      <strong>{{ t('customer.address') }}:</strong> {{ customer.address }}<br />
      <strong >Hình thức thanh toán:</strong> Tiền mặt hoặc chuyển khoản<br/>
      <strong>{{ t('sale_order.created_date') }}:</strong> {{ createDate }}
    </div>

    <table class="invoice-table">
      <thead>
        <tr>
          <th>{{ t('sale_order.number') }}</th>
          <th>{{ t('sale_order.product') }}</th>
          <th>{{ t('sale_order.si_quantity') }}</th>
          <th>{{ t('sale_order.si_unitprice') }}</th>
          <th>{{ t('sale_order.si_totalprice') }}</th>
          <th>{{ t('sale_order.si_note') }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in saleOrderItems" :key="index">
          <td>{{ index + 1 }}</td>
          <td>{{ item.name }}</td>
          <td>{{ item.quantity }}</td>
          <td>{{ formatCurrency(item.unitPrice) }}</td>
          <td>{{ formatCurrency(item.unitPrice * item.quantity) }}</td>
          <td>{{ item.note }}</td>
        </tr>
      </tbody>
    </table>

    <div class="total">
      <strong>{{ t('sale_order.total_amound') }}: {{ formatCurrency(totalAmount) }}</strong>

    </div>
    <div class="total">
      <strong>Thuế 8%: {{ formatCurrency((totalAmount*8)/100) }}</strong>

    </div>
    <div class="total">
      <strong>Tổng cộng tiền thanh toán: {{ formatCurrency(totalAmount+((totalAmount*8)/100)) }}</strong>

    </div>
    <div class="total">

      <strong>{{ t('sale_order.total_amound_paid') }}: {{ formatCurrency(props.paid) }}</strong>
    </div>
    <div class="total">

      <strong>{{ t('sale_order.dept') }}: {{ formatCurrency((totalAmount+((totalAmount*8)/100)) - props.paid) }}</strong>
    </div>

    <div class="print-button">
      <button @click="downloadPdf" :loading="loading">{{ t('sale_order.download_pdf') }}</button>
      <button @click="printInvoice" :loading="loading">{{ t('sale_order.print') }}</button>
    </div>
  </div>

</template>

<script lang="ts" setup>

import { ref, computed, nextTick, reactive, onMounted, watch } from 'vue'
import QRCode from 'qrcode'
import { defineProps } from 'vue'
import * as ui from '../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import jsPDF from 'jspdf'
import html2canvas from 'html2canvas-pro';
import api, { SaleOrderRequest, Customer, Business  } from '../services/api';
const loading = ref(false)
const props = defineProps<{
  saleOrderId: string
  paid: number
  businessId: string
}>()

const qrCodeUrl = ref('')
const customer = ref<Customer>({
  id: null,
  name: null,
  phone: null,
  dob: null,
  address: null,
  customerType: null,
  mst: null,
  cmnd: null,
  email: null,
  note: null,
  companyName: null,
  businessId: null,
})
let saleOrder: SaleOrderRequest = reactive({
  totalAmount: 0,
  totalAmountPaid: 0,
  customerId: null,
  subStatus: 'None',
  status: 'None',
  saleOrderItemsRequestDTO: [{
    id: null,
    productId: null,
    SaleOrdersId: null,
    quantity: 0,
    unitPrice: 0,
    note: null
  }],
  businessId: null,
  qrcodeId: null
});

const business = ref<Business>({
    id: null,
    name: '',
    mst: '',
    email: '',
    phone: '',
    address: '',
    bankName: '',
    bankAccount: ''
})
const saleOrderItems = ref([])
const createDate = ref(null)


const totalAmount = computed(() => {
  return saleOrderItems.value.reduce(
    (sum, item) => sum + (item.unitPrice || 0) * (item.quantity || 0),
    0
  );
});
watch(totalAmount, (newTotal) => {
  saleOrder.totalAmount = newTotal;

});

function formatCurrency(value: number): string {
  return value.toLocaleString('vi-VN') + ' ₫'
}

async function fetch() {
  try {
    loading.value = true;
    const saleOrderRes = await api.api.saleOrder.getSaleOrder(String(props.saleOrderId))
    Object.assign(saleOrder, saleOrderRes)
    customer.value = saleOrder['customer']
    createDate.value = new Date(saleOrderRes['createdAt']).toLocaleDateString()
    const saleOrderItemsRes = await api.api.saleOrderItem.getSaleItemsbySaleOrder(String(props.saleOrderId))
    saleOrderItems.value = saleOrderItemsRes.map((item) => ({
      id: item.id,
      productId: item['product']['id'],
      // name: productOptions.value.find((item1) => item1.value === item['product']['id'])?.label,
      name: '',
      quantity: item.quantity,
      unitPrice: item.unitPrice,
      note: item.note
    }))

    const productRes = await api.api.product.getProducts(props.businessId);
    saleOrderItems.value.forEach(item => {
      item.name = productRes.find(product => product.id === item.productId)?.name || '';
    });
    loading.value = false;

    const res_user = await api.api.user.getCurrentUser()
    const res_business = await api.api.business.getBusiness(res_user['businessId'])
    business.value = res_business
  } catch {
    ui.error(t('error.unknown'))
  }
}
async function downloadPdf() {
  try {
    loading.value = true
    const printButton = document.querySelector('.print-button') as HTMLElement
    const element = document.querySelector('.invoice-wrapper') as HTMLElement
    if (printButton) printButton.style.display = 'none'

    await nextTick()
    if (!element) return

    const canvas = await html2canvas(element, {
      scale: 2,
      useCORS: true,
      backgroundColor: '#ffffff',
      windowWidth: document.body.scrollWidth,
      windowHeight: document.body.scrollHeight
    });

    const imgData = canvas.toDataURL('image/png');
    const pdf = new jsPDF('p', 'mm', 'a4');

    const pdfWidth = pdf.internal.pageSize.getWidth(); // 210mm
    const pdfHeight = pdf.internal.pageSize.getHeight(); // 297mm

    // Kích thước ảnh thực tế (pixel), cần quy đổi ra mm
    const pxToMm = (px: number) => px * 0.264583; // 1px = ~0.264583mm
    const imgWidthMm = pxToMm(canvas.width);
    const imgHeightMm = pxToMm(canvas.height);

    // Tính tỷ lệ scale để vừa trang A4
    const scale = Math.min(pdfWidth / imgWidthMm, pdfHeight / imgHeightMm);

    const finalWidth = imgWidthMm * scale;
    const finalHeight = imgHeightMm * scale;

    const offsetX = (pdfWidth - finalWidth) / 2;
    const offsetY = (pdfHeight - finalHeight) / 2;

    pdf.addImage(imgData, 'PNG', offsetX, offsetY, finalWidth, finalHeight);

    const blobUrl = URL.createObjectURL(pdf.output('blob'));
    const link = document.createElement('a');
    link.href = blobUrl;
    link.download = `HoaDon_${props.saleOrderId}.pdf`;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);

    printButton.style.display = 'block'
    loading.value = false
    ui.success('Tải thành công')
  } catch (e) {
    console.error(e)
    ui.error(t('error.unknown'))
  }
}


function printInvoice() {
  try {
    const printWindow = window.open('', '_blank')
    if (!printWindow) return

    const styles = `
      <style>
      .qr-code {
  float: right;
  margin-left: 16px;
}
        body {
          font-family: Arial, sans-serif;
          display: flex;
          justify-content: center;
          padding: 40px;
        }

        .invoice-wrapper {
          width: 600px;
          padding: 20px;
          border: 1px solid #000;
        }

        .title {
          font-size: 20px;
          font-weight: bold;
          text-align: center;
          margin-bottom: 20px;
        }

        .info {
          margin-bottom: 15px;
        }

        .invoice-table {
          width: 100%;
          border-collapse: collapse;
        }

        .invoice-table th,
        .invoice-table td {
          border: 1px solid #000;
          padding: 8px;
          text-align: center;
        }

        .invoice-table th {
          background-color: #f2f2f2;
        }

        .total {
          margin-top: 10px;
          text-align: right;
          font-weight: bold;
        }

        .print-button {
          display: none;
        }

        @media print {
          .print-button {
            display: none !important;
          }
        }
      </style>
    `

    const html = `
      <html>
        <head>
          <title>Hóa đơn</title>
          ${styles}
        </head>
        <body>
          ${document.querySelector('.invoice-wrapper')?.outerHTML}
        </body>
      </html>
    `

    printWindow.document.write(html)
    printWindow.document.close()
    printWindow.focus()
    setTimeout(() => {
      printWindow.print()
      printWindow.close()
    }, 500)
    ui.success('print sucessfull')
  } catch {
    ui.error(t('error.unknown'))
  }
}
onMounted(async () => {
  await fetch()
  qrCodeUrl.value = await QRCode.toDataURL(String(props.saleOrderId))
})
</script>

<style scoped>
.qr-code {
  float: right;
  margin-left: 16px;
}

.invoice-wrapper {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.title {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 16px;
}

.info {
  margin-bottom: 16px;
}

.invoice-table {
  width: 100%;
  border-collapse: collapse;
}

.invoice-table th,
.invoice-table td {
  border: 1px solid #000;
  padding: 8px;
  text-align: center;
}

.invoice-table th {
  background-color: #f2f2f2;
}

.total {
  text-align: right;
  margin-top: 10px;
  font-weight: bold;
}

.print-button {
  text-align: center;
  margin-top: 20px;
}

@media print {
  .print-button {
    display: none !important;
  }
}
@media (max-width: 768px) {
  .invoice-wrapper {
    transform: scale(0.8);
    transform-origin: top left;
  }
}
</style>
