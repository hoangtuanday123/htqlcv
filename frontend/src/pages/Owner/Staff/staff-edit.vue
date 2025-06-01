<template>
  <q-page class="q-pa-md">
      <h1>Edit User</h1>
      <q-form class="q-gutter-md" @submit="save" autocorrect="off" autocapitalize="off" autocomplete="off"
          spellcheck="false">

          <q-input v-model="user.username" label="Username" readonly />

          <q-input type="email" v-model="user.email" label="Email" required />
          <q-input v-model="user.phoneNumber" label="Phone Number" />

          <div class="row">
              <div class="col q-gutter-md">
                  <q-btn label="Save" icon="check" :loading="loading" type="submit" color="primary" />
                  <q-btn label="Close" icon="close" type="button" to="../../staffs" outline color="grey-9" />
              </div>
          </div>
      </q-form>
  </q-page>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRoute } from 'vue-router'
import api, { User } from '../../../services/api';

const route = useRoute()
const loading = ref(false)


let user: User = reactive({
  id: 0,
  username: '',
  email: '',
  roles: [] as string[],
  phoneNumber: '',
  password: ' ',
  businessId:null
})

async function fetch() {
  loading.value = true;

  const res = await api.api.user.getUser(route.params.id as string)
  console.log(res)
  Object.assign(user, res)
  user.password = ''
  loading.value = false;
}

async function save() {
  loading.value = true
  await api.api.user.updateUser(route.params.id as string, user)
  loading.value = false
}

onMounted(async () => {
  await fetch()
})

</script>
