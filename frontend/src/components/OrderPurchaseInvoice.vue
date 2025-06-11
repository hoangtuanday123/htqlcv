<template>
  <div class="invoice-wrapper">
    <div class="title">{{ t('purchase_order.title') }}</div>
    <div class="qr-code">
      <img :src="qrCodeUrl" alt="QR Code" width="150" height="150" />
    </div>
    <div class="info">
      <strong>{{ t('purchase_order.ID') }}:</strong> {{ props.purchaseOrderId }}<br />
      <strong>{{ t('purchase_order.product_supplier') }}:</strong> {{ supplier.name }}<br />
      <strong>{{ t('supplier.phone') }}:</strong> {{ supplier.phone }}<br />
      <strong>{{ t('supplier.address') }}:</strong> {{ supplier.address }}<br />
      <strong>{{ t('supplier.tax-code') }}:</strong> {{ supplier.mst }}<br />
      <strong>{{ t('purchase_order.created_date') }}:</strong> {{ createDate }}
    </div>

    <table class="invoice-table">
      <thead>
        <tr>
          <th>{{ t('purchase_order.number') }}</th>
          <th>{{ t('purchase_order.product') }}</th>
          <th>{{ t('purchase_order.pi_quantity') }}</th>
          <th>{{ t('purchase_order.pi_unitprice') }}</th>
          <th>{{ t('purchase_order.pi_totalprice') }}</th>
          <th>{{ t('purchase_order.pi_note') }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in purchaseOrderItems" :key="index">
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
      <strong>{{ t('purchase_order.total_amound') }}: {{ formatCurrency(totalAmount) }}</strong>

    </div>
    <div class="total">

      <strong>{{ t('purchase_order.total_amound_paid') }}: {{ formatCurrency(props.paid) }}</strong>
    </div>
    <div class="total">

      <strong>{{ t('purchase_order.dept') }}: {{ formatCurrency(totalAmount - props.paid) }}</strong>
    </div>

    <div class="print-button">
      <button @click="downloadPdf" :loading="loading">{{ t('purchase_order.download_pdf') }}</button>
      <button @click="printInvoice" :loading="loading">{{ t('purchase_order.print') }}</button>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, nextTick, reactive, onMounted, watch } from 'vue'
import { defineProps } from 'vue'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
// import html2pdf from 'html2pdf.js'
import jsPDF from 'jspdf'
import html2canvas from 'html2canvas-pro';
import api, { PurchaseOrderRequest, Supplier } from '../services/api';
import * as ui from '../utils/ui'
import QRCode from 'qrcode'
const loading = ref(false)
const props = defineProps<{
  purchaseOrderId: string
  paid: number
  businessId: string
}>()
const qrCodeUrl = ref('')
const supplier = ref<Supplier>({
  id: null,
  name: null,
  phone: null,
  address: null,
  branch: null,
  mst: null,
  email: null,
  note: null,
  company: null,
  businessId: null,
})
let purchaseOrder: PurchaseOrderRequest = reactive({
  totalAmount: 0,
  totalAmountPaid: 0,
  supplierId: null,
  subStatus: 'None',
  status: 'None',
  purchaseOrderItemsRequestDTO: [{
    id: null,
    productId: null,
    purchaseOrdersId: null,
    quantity: 0,
    unitPrice: 0,
    note: null
  }],
  businessId: null,
  qrcodeId: null
});

const createDate = ref(null)
const purchaseOrderItems = ref([])


const totalAmount = computed(() => {
  return purchaseOrderItems.value.reduce(
    (sum, item) => sum + (item.unitPrice || 0) * (item.quantity || 0),
    0
  );
});
watch(totalAmount, (newTotal) => {
  purchaseOrder.totalAmount = newTotal;

});

function formatCurrency(value: number): string {
  return value.toLocaleString('vi-VN') + ' ₫'
}

async function fetch() {
  try {
    loading.value = true;
    const purchaseOrderRes = await api.api.purchaseOrder.getPurchaseOrder(String(props.purchaseOrderId))
    Object.assign(purchaseOrder, purchaseOrderRes)
    supplier.value.name = purchaseOrderRes['supplier']['name']
    supplier.value.phone = purchaseOrderRes['supplier']['phone']
    supplier.value.address = purchaseOrderRes['supplier']['address']
    supplier.value.mst = purchaseOrderRes['supplier']['mst']
    createDate.value = new Date(purchaseOrderRes['createdAt']).toLocaleDateString()
    const purchaseOrderItemsRes = await api.api.purchaseOrderItem.getPurchaseOrderItemsbyPurchaseOrder(String(props.purchaseOrderId))
    purchaseOrderItems.value = purchaseOrderItemsRes.map((item) => ({
      id: item.id,
      productId: item['product']['id'],
      name: '',
      quantity: item.quantity,
      unitPrice: item.unitPrice,
      note: item.note
    }))

    const productRes = await api.api.product.getProducts(props.businessId);
    purchaseOrderItems.value.forEach(item => {
      item.name = productRes.find(product => product.id === item.productId)?.name || '';
    });
    loading.value = false;
  } catch {
    ui.error(t('error.unknown'))
  }
}
async function downloadPdf() {
  try {
    loading.value = true
    const printButton = document.querySelector('.print-button') as HTMLElement
    const element = document.querySelector('.invoice-wrapper') as HTMLElement;
    if (printButton) printButton.style.display = 'none'
    await nextTick()
    if (!element) return;

    const canvas = await html2canvas(element, {
      scale: 2,
      useCORS: true,
      backgroundColor: '#ffffff'
    });

    const imgData = canvas.toDataURL('image/png');
    const pdf = new jsPDF('p', 'mm', 'a4');

    const pdfWidth = pdf.internal.pageSize.getWidth();
    const pdfHeight = (canvas.height * pdfWidth) / canvas.width;

    pdf.addImage(imgData, 'PNG', 0, 0, pdfWidth, pdfHeight);
    pdf.save(`HoaDon_${props.purchaseOrderId}.pdf`);
    printButton.style.display = 'block'
    loading.value = false
    ui.success('download sucessfull')
  } catch {
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
  qrCodeUrl.value = await QRCode.toDataURL(String(props.purchaseOrderId))
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
</style>
