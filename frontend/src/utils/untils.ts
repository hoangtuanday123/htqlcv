import QRCode from 'qrcode';
import jsPDF from 'jspdf';
export async function generateQRcode(productid) {
    try {
        // const now = new Date();
        // const timestamp = now.toISOString().replace(/[-:.TZ]/g, '').slice(0, 15);
        // const outputPath = `${process.env.QRCODE_PRODUCT_FOLDER}/${product_id}_${timestamp}.png`;
        const dataUrl = await QRCode.toDataURL(productid, {
            errorCorrectionLevel: 'low',
            margin: 1,
            scale: 4,
        });
        return dataUrl
    } catch (err) {
        console.error('❌ Error generating QR code:', err);
    }
}

export async function downloadQRcode(dataUrl, fileName) {
    try {
        const a = document.createElement('a');
        a.href = dataUrl;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    } catch (err) {
        console.error('❌ Error downloading QR code:', err);
    }
}

function removeVietnameseTones(str: string): string {
    return str
        .normalize('NFD')                     // Tách dấu khỏi ký tự gốc
        .replace(/[\u0300-\u036f]/g, '')      // Xóa các dấu (dấu sắc, huyền, hỏi, ngã, nặng, mũ, trăng...)
        .replace(/đ/g, 'd')                   // Chuyển đ -> d
        .replace(/Đ/g, 'D')                   // Chuyển Đ -> D
        .replace(/[^a-zA-Z0-9 ]/g, '')        // Loại bỏ ký tự đặc biệt (nếu muốn)
        .trim();                              // Loại bỏ khoảng trắng đầu/cuối (nếu cần)
}


export async function printQRcodeAsPDF(
    dataUrl: string,
    fileName: string,
    num: number = 1,
) {
    try {
        const pdf = new jsPDF();

        const qrSize = 50; // mm
        const margin = 10;
        const cols = 3;
        const rows = 5;
        const maxPerPage = cols * rows;
        const titleHeight = 10;

        for (let i = 0; i < num; i++) {

            const indexInPage = i % maxPerPage;
            const col = indexInPage % cols;
            const row = Math.floor(indexInPage / cols);

            // Tính vị trí: trừ ra thêm 10 để chừa chỗ tiêu đề
            const x = margin + col * (qrSize + margin);
            const y = margin + titleHeight + row * (qrSize + margin);

            // Nếu là trang mới → addPage và thêm tiêu đề
            if (i !== 0 && indexInPage === 0) {
                pdf.addPage();
            }

            // Nếu là dòng đầu tiên của trang → ghi tên sản phẩm
            if (indexInPage === 0) {
                pdf.setFontSize(14);
                pdf.text(`San pham: ${removeVietnameseTones(fileName)}`, margin, margin + 5);
            }

            // Vẽ QR code
            pdf.addImage(dataUrl, 'PNG', x, y, qrSize, qrSize);
        }

        pdf.save(fileName.endsWith('.pdf') ? fileName : `${fileName}.pdf`);
    } catch (err) {
        console.error('❌ Error printing QR code to PDF:', err);
    }
}


