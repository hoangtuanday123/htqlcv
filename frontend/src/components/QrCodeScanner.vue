<template>
    <q-page padding class="flex flex-center column">
        <q-btn color="negative" label="Dừng quét" @click="stopScanner" />

        <video ref="video" width="320" height="240" class="q-mt-md" />

        <div v-if="result" class="q-mt-md text-primary">
            Kết quả: <strong>{{ result }}</strong>
        </div>
    </q-page>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, defineEmits } from 'vue';
import { BrowserMultiFormatReader } from '@zxing/library';

const emit = defineEmits(['scanned', 'close']);

const video = ref(null);
const result = ref('');
let codeReader = null;

const startScanner = async () => {
    codeReader = new BrowserMultiFormatReader();

    try {
        await codeReader.decodeFromVideoDevice(
            null,
            video.value,
            (resultObj, err) => {
                if (resultObj) {
                    result.value = resultObj.getText();
                    emit('scanned', result.value);
                    stopScanner();
                }
                if (err && !(err.name === 'NotFoundException')) {
                    console.error(err);
                }
            }
        );
    } catch (error) {
        console.error('Lỗi khi mở camera:', error);
    }
};

const stopScanner = () => {
    if (codeReader) {
        codeReader.reset();
        codeReader = null;
    }
    emit('close');
};

onMounted(() => {
    startScanner();
});

onBeforeUnmount(() => {
    if (codeReader) codeReader.reset();
});
</script>