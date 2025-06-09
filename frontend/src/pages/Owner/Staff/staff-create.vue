<template>
  <q-page class="q-pa-md">
    <h2>{{ t('staff.create') }}</h2>
    <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
      spellcheck="false">
      <q-input stack-label v-model="user.username" :label="t('staff.username')" required />

      <q-input type="email" v-model="user.email" :label="t('staff.email')" />

      <q-input type="password" v-model="user.password" :label="t('staff.password')" />

      <div class="row">
        <div class="col q-gutter-md">
          <q-btn :label="t('button.save')" icon="check" :loading="loading" type="submit" color="primary" />
          <q-btn :label="t('button.close')" icon="close" type="button" to="../staffs" outline text-color="text-color" />
        </div>
      </div>
    </q-form>
  </q-page>
</template>
<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import api, { User } from '../../../services/api';
import * as ui from '../../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info
const router = useRouter()
const loading = ref(false)


let user: User = reactive({
  id: null,
  username: '',
  password: '',
  email: '',
  phoneNumber: '',
  roles: [],
  businessId: null
})


async function save() {
  try {
    loading.value = true
    user.businessId = userInfo.value.businessId
    await api.api.user.createStaff(user)
    loading.value = false
    ui.success(t('success.save'))
    router.push({ path: '../staffs' })
  } catch {
    ui.error(t('error.unknown'))
  }
}


</script>