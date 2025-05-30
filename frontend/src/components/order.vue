<template>
  <div class="invoice-wrapper">
    <div class="title">HÓA ĐƠN THANH TOÁN</div>

    <div class="info">
      <strong>Khách hàng:</strong> {{ customerName }}<br />
      <strong>Ngày:</strong> {{ invoiceDate }}
    </div>

    <table class="invoice-table">
      <thead>
        <tr>
          <th>STT</th>
          <th>Sản phẩm</th>
          <th>Số lượng</th>
          <th>Đơn giá</th>
          <th>Thành tiền</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in items" :key="index">
          <td>{{ index + 1 }}</td>
          <td>{{ item.name }}</td>
          <td>{{ item.quantity }}</td>
          <td>{{ formatCurrency(item.price) }}</td>
          <td>{{ formatCurrency(item.price * item.quantity) }}</td>
        </tr>
      </tbody>
    </table>

    <div class="total">
      <strong>Tổng cộng: {{ formatCurrency(total) }}</strong>
    </div>

    <div class="print-button">
      <button @click="downloadPdf">Tải PDF</button>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed } from 'vue'
// import html2pdf from 'html2pdf.js'
import jsPDF from 'jspdf'
import html2canvas from 'html2canvas'
interface Item {
  name: string
  quantity: number
  price: number
}

const customerName = ref('Nguyễn Văn A')
const invoiceDate = ref(new Date().toLocaleDateString())

const items = ref<Item[]>([
  { name: 'Sản phẩm A', quantity: 2, price: 50000 },
  { name: 'Sản phẩm B', quantity: 1, price: 150000 },
  { name: 'Sản phẩm C', quantity: 3, price: 30000 }
])

const total = computed(() =>
  items.value.reduce((sum, item) => sum + item.quantity * item.price, 0)
)

function formatCurrency(value: number): string {
  return value.toLocaleString('vi-VN') + ' ₫'
}
function downloadPdf(): void {
  const element = document.querySelector('.invoice-wrapper') as HTMLElement | null
  if (!element) {
    alert('Không tìm thấy phần tử để in!')
    return
  }

  html2canvas(element, { scale: 2 }).then((canvas: HTMLCanvasElement) => {
    const imgData = canvas.toDataURL('image/png')

    const pdf = new jsPDF('p', 'pt', 'a4')
    const pageWidth = pdf.internal.pageSize.getWidth()
    const pageHeight = pdf.internal.pageSize.getHeight()

    const imgWidth = pageWidth - 40 // lề 20pt 2 bên
    const imgHeight = (canvas.height * imgWidth) / canvas.width

    pdf.addImage(imgData, 'PNG', 20, 20, imgWidth, imgHeight)

    const nameElement = document.getElementById('emp_name')
    let filename = 'document.pdf'
    if (nameElement && nameElement.getAttribute('data-value')) {
      filename = `payslip_${nameElement.getAttribute('data-value')}.pdf`
    }

    pdf.save(filename)
  })
}
function printInvoice() {
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
}
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
