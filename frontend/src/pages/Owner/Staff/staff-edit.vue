<template>
  <q-page class="q-pa-md">
      <h1>{{t('staff.update')}}</h1>
      <q-form class="q-gutter-md" @submit="save" autocorrect="off" autocapitalize="off" autocomplete="off"
          spellcheck="false">

          <q-input v-model="user.username" :label="t('staff.username')" readonly />

          <q-input type="email" v-model="user.email" :label="t('staff.email')" required />
          <q-input v-model="user.phoneNumber" :label="t('staff.phone')" />

          <div class="row">
              <div class="col q-gutter-md">
                  <q-btn :label="t('button.save')" icon="check" :loading="loading" type="submit" color="primary" />
                  <q-btn :label="t('button.close')" icon="close" type="button" to="../../staffs" outline color="grey-9" />
              </div>
          </div>
      </q-form>
  </q-page>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import { useRoute } from 'vue-router'
import api, { User } from '../../../services/api';
import * as ui from '../../../utils/ui'

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
  try{
    loading.value = true;

    const res = await api.api.user.getUser(route.params.id as string)
    console.log(res)
    Object.assign(user, res)
    user.password = ''
    loading.value = false;
  } catch {
   ui.error(t('error.unknown'))
  }
}

async function save() {
  try{
    loading.value = true
    await api.api.user.updateUser(route.params.id as string, user)
    loading.value = false
    ui.success(t('success.save'))
  } catch {
   ui.error(t('error.unknown'))
  }
}

onMounted(async () => {
  await fetch()
})

</script>
