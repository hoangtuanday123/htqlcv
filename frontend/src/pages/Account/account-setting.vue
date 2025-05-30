<template>
    <q-page class="q-pa-md">
        <h1>Account</h1>
        <div class="row justify-end">
            <q-btn label="Change Password" icon="refresh" type="button" to="/account/password" color="accent" />
        </div>
        <q-tabs v-model="tab" dense class="text-grey" active-color="primary" indicator-color="primary" align="justify"
            narrow-indicator>
            <q-tab name="information" label="Information" />
            <q-tab name="business" label="Business" />
        </q-tabs>
        <q-separator />
        <q-tab-panels v-model="tab" animated>
            <q-tab-panel name="information">

                <q-form class="q-gutter-md" @submit="saveUser" autocorrect="off" autocapitalize="off" autocomplete="off"
                    spellcheck="false">

                    <q-input v-model="user.username" readonly label="Username" />

                    <q-input type="email" v-model="user.email" readonly label="Email" />
                    <q-input type="text" v-model="user.phoneNumber" label="Phone Number" />


                    <div class="row">
                        <div class="col q-gutter-md">
                            <q-btn label="Save" icon="check" :loading="loading" type="submit" color="primary" />
                            <q-btn label="Close" icon="close" type="button" to="/" outline color="white"
                                text-color="black" />
                        </div>
                    </div>
                </q-form>
            </q-tab-panel>
            <q-tab-panel name="business">

                <q-form class="q-gutter-md" @submit="saveBusiness" autocorrect="off" autocapitalize="off"
                    autocomplete="off" spellcheck="false">

                    <q-input filled v-model="business.name" label="Business Name *" hint="Business Name" lazy-rules
                        :rules="[val => val && val.length > 0 || 'Please type something']" />

                    <q-input filled v-model="business.mst" label="Mst " hint="mst" />
                    <q-input filled v-model="business.email" label="Email " hint="email" />
                    <q-input filled v-model="business.phone" label="Phone Number *" hint="Phone Number" lazy-rules
                        :rules="[val => val && val.length > 0 || 'Please type something']" />


                    <div class="row">
                        <div class="col q-gutter-md">
                            <q-btn label="Save" icon="check" :loading="loading" type="submit" color="primary" />
                            <q-btn label="Close" icon="close" type="button" to="/" outline color="white"
                                text-color="black" />
                        </div>
                    </div>
                </q-form>
            </q-tab-panel>
        </q-tab-panels>
    </q-page>
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue';
import api, { User, Business } from '../../services/api';
import { RefSymbol } from '@vue/reactivity';
import { userStore } from '../../stores/user'
import pinia from '../../stores'
const _userStore = userStore(pinia())
const tab = ref('information')
const loading = ref(false)
const user = ref<User>({
    id: null,
    username: '',
    email: '',
    roles: [],
    phoneNumber: '',
    password: '',
    businessId: null
})
const role = ref(null)
const business = ref<Business>({
    id: null,
    name: '',
    mst: '',
    email: '',
    phone: ''
})

async function fetch() {
    loading.value = true;

    const res_user = await api.api.user.getCurrentUser()
    user.value.id = res_user['id']
    user.value.username = res_user['username']
    user.value.email = res_user['email']
    role.value = res_user['roles']
    user.value.phoneNumber = res_user['phoneNumber']
    user.value.businessId = res_user['businessId']


    const res_business = await api.api.business.getBusiness(user.value.businessId)
    business.value = res_business
    loading.value = false;
}

async function saveUser() {
    loading.value = true;
    await api.api.user.updateUser(user.value.id, user.value)
    _userStore.saveUserInfo({ id: user.value.id, username: user.value.username, email: user.value.email, phoneNumber: user.value.phoneNumber, roles: role.value, businessId: user.value.businessId })
    loading.value = false
}

async function saveBusiness() {
    loading.value = true;
    console.log(business.value)
    await api.api.business.updateBusiness(business.value.id, {
        name: business.value.name,
        mst: business.value.mst, email: business.value.email, phone: business.value.phone
    })
    loading.value = false
}
onMounted(async () => {
    await fetch()
})
</script>