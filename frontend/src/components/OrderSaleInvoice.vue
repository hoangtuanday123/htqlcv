<template>
  <div class="invoice-wrapper">
    <div class="title">{{ t('sale_order.title') }}</div>

    <div class="info">
      <strong>Mã:</strong> {{ props.saleOrderId }}<br />
      <strong v-if="customer.customerType=='individual'">{{ t('customer.title') }}:</strong> {{ customer.name }}<br v-if="customer.customerType=='individual'"/>
      <strong v-if="customer.customerType=='companyCustomer'">{{ t('customer.company_name') }}:</strong> {{ customer.companyName }}<br v-if="customer.customerType=='companyCustomer'"/>
      <strong v-if="customer.customerType=='companyCustomer'">{{ t('customer.tax_code') }}:</strong> {{ customer.companyName }}:</strong> {{ customer.mst }}<br v-if="customer.customerType=='companyCustomer'"/>
      <strong>{{ t('customer.phone') }}:</strong> {{ customer.companyName }}:</strong> {{ customer.phone }}<br />
      <strong>{{ t('customer.address') }}:</strong> {{ customer.companyName }}:</strong> {{ customer.address }}<br />
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

      <strong>{{ t('sale_order.total_amound_paid') }}: {{ formatCurrency(props.paid) }}</strong>
    </div>
    <div class="total">

    <strong>{{ t('sale_order.dept') }}: {{ formatCurrency(totalAmount-props.paid) }}</strong>
    </div>

    <div class="print-button" >
      <button @click="downloadPdf" :loading="loading">{{ t('sale_order.download_pdf') }}</button>
      <button @click="printInvoice" :loading="loading">{{ t('sale_order.print') }}</button>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, nextTick, reactive, onMounted, watch } from 'vue'
import { defineProps } from 'vue'
import * as ui from '../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import jsPDF from 'jspdf'
import html2canvas from 'html2canvas-pro';
import api, { SaleOrderRequest,Customer } from '../services/api';
const loading=ref(false)
const props = defineProps<{
  saleOrderId: string
  paid:number
  businessId:string
}>()
const customer=ref<Customer>({
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
  subStatus: "None",
  status: "None",
  saleOrderItemsRequestDTO: [{
    id: null,
    productId: null,
    SaleOrdersId: null,
    quantity: 0,
    unitPrice: 0,
    note: null
  }],
  businessId: null
});
const saleOrderItems = ref([])
const createDate=ref(null)


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

async function fetch(){
  try{
    loading.value = true;
    const saleOrderRes = await api.api.saleOrder.getSaleOrder(String(props.saleOrderId))
    Object.assign(saleOrder, saleOrderRes)
    customer.value=saleOrder['customer']
    createDate.value= new Date(saleOrderRes['createdAt']).toLocaleDateString()
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
    } catch {
   ui.error(t('error.unknown'))
  }
}
async function downloadPdf() {
  try{
    loading.value=true
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
    pdf.save(`HoaDon_${props.saleOrderId}.pdf`);
    printButton.style.display = 'block'
    loading.value=false
    ui.success("download sucessfull")
  } catch {
   ui.error(t('error.unknown'))
  }
}
function printInvoice() {
  try{
    const printWindow = window.open('', '_blank')
    if (!printWindow) return

    const styles = `
      <style>
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
    ui.success("print sucessfull")
  } catch {
   ui.error(t('error.unknown'))
  }
}
onMounted(async () => {
  await fetch()
})
</script>

<style scoped>
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
