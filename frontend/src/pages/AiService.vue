<template>
  <q-page class="column items-center justify-start q-pa-md">
    <div style="width: 100%; max-width: 500px">
      <div v-for="(msg, index) in messages" :key="index">
        <q-chat-message :name="msg.name" :text="msg.text" :sent="msg.name === 'me'" />
      </div>

      <div class="row q-mt-md">
        <q-input outlined v-model="userMessage" placeholder="Nhập tin nhắn..." class="col-grow"
          @keyup.enter="sendMessage" />
        <q-btn label="Gửi" color="primary" @click="sendMessage" :loading="loading" class="q-ml-sm" />
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
const loading = ref(false)
// Danh sách tin nhắn
const messages = ref([
  { name: 'system', text: ['Chào bạn, tôi có thể giúp gì?'] }
])

const userMessage = ref('')

async function sendMessage() {
  loading.value = true
  if (!userMessage.value.trim()) return

  // Thêm tin nhắn người dùng
  const question = userMessage.value
  messages.value.push({ name: 'me', text: [question] })
  userMessage.value = ''

  // Giới hạn thời gian chờ phản hồi là 2 giây
  const TIMEOUT_MS = 10000

  try {
    const request = { message: question, businessId: String(userInfo.value.businessId) }

    const timeoutPromise = new Promise((_, reject) =>
      setTimeout(() => reject(new Error('timeout')), TIMEOUT_MS)
    )

    const reply = await Promise.race([
      api.api.aiService.getAiService(request),
      timeoutPromise
    ])
    loading.value = false
    // Nếu trả lời hợp lệ
    messages.value.push({ name: 'system', text: [reply['message']] })
  } catch (error) {
    console.warn('AI service error or timeout:', error)
    loading.value = false
    messages.value.push({ name: 'system', text: ['❌ Câu hỏi không hợp lệ hoặc không thể trả lời.'] })
  }
}


</script>