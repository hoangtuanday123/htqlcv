<template>
  <div class="q-pa-md">
    <q-stepper
      v-model="step"
      ref="stepper"
      color="primary"
      animated
    >
      <q-step
        :name="1"
        title="Register Business"
        icon="business_center"
        :done="step > 1"
      >
      <q-form ref="formStep1">
        <q-input filled v-model="business.name" label="Business Name *" hint="Business Name" lazy-rules
        :rules="[val => val && val.length > 0 || 'Please type something']" />

        <q-input filled v-model="business.mst" label="Mst " hint="mst"  />
        <q-input filled v-model="business.email" label="Email " hint="email"  />
        <q-input filled v-model="business.phone" label="Phone Number *" hint="Phone Number" lazy-rules
        :rules="[val => val && val.length > 0 || 'Please type something']" />
      </q-form>
      </q-step>

      <q-step
        :name="2"
        title="Register User"

        icon="person "
        :done="step > 2"
      >
      <q-form ref="formStep2">
        <q-input stack-label v-model="user.username" label="Username" required />

        <q-input type="email" v-model="user.email" label="Email" />

        <q-input type="password" v-model="user.password" label="Password" />
      </q-form>
      </q-step>



      <template v-slot:navigation>
        <q-stepper-navigation>
          <q-btn @click="next()" color="primary" :label="step === 2 ? 'Finish' : 'Continue'" />
          <q-btn v-if="step > 1" flat color="primary" @click="$refs.stepper.previous()" label="Back" class="q-ml-sm" />
        </q-stepper-navigation>
      </template>
    </q-stepper>
  </div>
</template>


<script setup lang="ts">

import { reactive, ref } from 'vue'
import api, {BusinessRequest,User } from '../services/api';
import { useRouter } from 'vue-router';
import { QForm } from 'quasar';
const route = useRouter();
const step=ref(1);
const formStep1 = ref<QForm | null>(null)
const formStep2 = ref<QForm | null>(null)
let business:BusinessRequest=reactive({
  name:'',
  email:'',
  phone:'',
  mst:''
})

let user: User = reactive({
    id: null,
    username: '',
    password: '',
    email: '',
    phoneNumber: '',
    roles: [],
    business_id:null
})

async function next() {
  if(step.value===1){
    const isValid = await formStep1.value.validate()
    if (!isValid) return
    step.value=2
  }
  else if(step.value===2){
    const isValid = await formStep2.value.validate()
    if (!isValid) return
    const businessId=await api.api.business.createBusiness(business)
    user.business_id=businessId
    await api.api.user.createUser(user)
    route.push({ path: '../login' })
  }


}
</script>