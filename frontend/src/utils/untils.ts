import QRCode from 'qrcode';
export async function generateQRcode(productUrl) {
    try {
        // const now = new Date();
        // const timestamp = now.toISOString().replace(/[-:.TZ]/g, '').slice(0, 15);
        // const outputPath = `${process.env.QRCODE_PRODUCT_FOLDER}/${product_id}_${timestamp}.png`;
        const dataUrl = await QRCode.toDataURL(productUrl, {
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
