<template>
  <div class="q-pa-md">
    <q-stepper v-model="step" ref="stepper" color="primary" animated>
      <q-step :name="1" :title="t('register.register_business')" icon="business_center" :done="step > 1">
        <q-form ref="formStep1">
          <q-input filled v-model="business.name" :label="t('register.business_name')"
            :hint="t('register.business_name')" lazy-rules
            :rules="[val => val && val.length > 0 || t('register.invalid')]" />

          <q-input filled v-model="business.mst" :label="t('register.tax_code')" :hint="t('register.tax-code')" />
          <q-input filled v-model="business.email" :label="t('register.email')" :hint="t('register.email')" />
          <q-input filled v-model="business.phone" :label="t('register.phone_number')"
            :hint="t('register.phone_number')" lazy-rules
            :rules="[val => val && val.length > 0 || t('register.invalid')]" />
          <q-input filled v-model="business.address" label="Địa chỉ" hint="Địa chỉ" />
          <q-input filled v-model="business.bankName" label="Tên ngân hàng" hint="Tên ngân hàng" />
          <q-input filled v-model="business.bankAccount" label="Tài khoản ngân hàng" hint="Tài khoản ngân hàng" />
        </q-form>
      </q-step>

      <q-step :name="2" :title="t('register.register_user')" icon="person " :done="step > 2">
        <q-form ref="formStep2">
          <q-input stack-label v-model="user.username" :label="t('register.username')" required />

          <q-input type="email" v-model="user.email" :label="t('register.email')" />

          <q-input type="password" v-model="user.password" :label="t('register.password')" />
        </q-form>
      </q-step>



      <template v-slot:navigation>
        <q-stepper-navigation>
          <q-btn @click="next()" color="primary" :label="step === 2 ? t('register.finish') : t('register.continue')" />
          <q-btn v-if="step > 1" flat color="primary" @click="stepper?.previous()" :label="t('register.back')"
            class="q-ml-sm" />
        </q-stepper-navigation>
      </template>
    </q-stepper>
  </div>
</template>


<script setup lang="ts">

import { reactive, ref } from 'vue'
import * as ui from '../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import api, { BusinessRequest, User } from '../services/api';
import { useRouter } from 'vue-router';
import { QForm } from 'quasar';
const route = useRouter();
import { QStepper } from 'quasar';
const step = ref(1);
const formStep1 = ref<QForm | null>(null)
const formStep2 = ref<QForm | null>(null)
const stepper = ref<QStepper | null>(null)
let business: BusinessRequest = reactive({
  name: '',
  email: '',
  phone: '',
  mst: '',
  address: '',
  bankName: '',
  bankAccount: ''
})

let user: User = reactive({
  id: null,
  username: '',
  password: '',
  email: '',
  phoneNumber: '',
  roles: [],
  businessId: null
})

async function next() {
  try {
    if (step.value === 1) {
      const isValid = await formStep1.value.validate()
      if (!isValid) return
      step.value = 2
    }
    else if (step.value === 2) {
      const isValid = await formStep2.value.validate()
      if (!isValid) return
      const businessId = await api.api.business.createBusiness(business)
      user.businessId = businessId
      await api.api.user.createUser(user)
      route.push({ path: '../login' })
    }
  } catch {
    ui.error(t('error.unknown'))
  }


}
</script>