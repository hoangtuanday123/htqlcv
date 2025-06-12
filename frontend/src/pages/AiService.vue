<template>
  <q-page class="column items-center justify-start q-pa-md">
    <div style="width: 100%; max-width: 500px">
      <div v-for="(msg, index) in messages" :key="index">
        <q-chat-message :name="msg.name" :text="msg.text" :sent="msg.name === 'me'" />
      </div>

      <div class="row q-mt-md">
        <q-input outlined v-model="userMessage" placeholder="Nhập tin nhắn..." class="col-grow"
          @keyup.enter="sendMessage" />
        <q-btn label="Gửi" color="primary" @click="sendMessage" class="q-ml-sm" />
      </div>
    </div>
  </q-page>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import api from '../services/api';
import { useCurrentuser } from '../share/currentuser';

const currentUser = useCurrentuser()
const userInfo = currentUser.info
// Danh sách tin nhắn
const messages = ref([
  { name: 'system', text: ['Chào bạn, tôi có thể giúp gì?'] }
])

const userMessage = ref('')

async function sendMessage() {
  if (!userMessage.value.trim()) return

  // Thêm tin nhắn người dùng
  messages.value.push({ name: 'me', text: [userMessage.value] })

  // Giả lập trả lời của hệ thống sau 0.5s (hoặc gọi API thật)
  const question = userMessage.value
  userMessage.value = ''
  const request = { message: question, businessId: String(userInfo.value.businessId) }
  console.log('Request to AI Service:', request)
  const reply = await api.api.aiService.getAiService(request)
  messages.value.push({ name: 'system', text: [reply['message']] })
  // setTimeout(async () => {
  //   const request = { message: question, business_id: String(userInfo.value.businessId) }
  //   console.log('Request to AI Service:', request)
  //   const reply = await api.api.aiService.getAiService(request)
  //   messages.value.push({ name: 'system', text: [reply['message']] })
  // }, 500)
}

</script>