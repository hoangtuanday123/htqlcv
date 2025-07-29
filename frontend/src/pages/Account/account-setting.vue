<template>
    <q-page class="q-pa-md">
        <h2>{{ t('account.title') }}</h2>
        <div class="row justify-end">
            <q-btn :label="t('account.change_password')" icon="refresh" type="button" to="/account/password"
                color="accent" />
        </div>
        <q-tabs v-model="tab" dense class="text-grey" active-color="primary" indicator-color="primary" align="justify"
            narrow-indicator>
            <q-tab name="information" :label="t('account.information')" />
            <q-tab name="business" :label="t('account.business')" />
        </q-tabs>
        <q-separator />
        <q-tab-panels v-model="tab" animated>
            <q-tab-panel name="information">

                <q-form class="q-gutter-md" @submit="saveUser" autocorrect="off" autocapitalize="off" autocomplete="off"
                    spellcheck="false">

                    <q-input v-model="user.username" readonly :label="t('account.username')" />

                    <q-input type="email" v-model="user.email" :label="t('account.email')" />
                    <q-input type="text" v-model="user.phoneNumber" :label="t('account.phone_number')" />


                    <div class="row">
                        <div class="col q-gutter-md">
                            <q-btn :label="t('button.save')" icon="check" :loading="loading" type="submit"
                                color="primary" />
                            <q-btn :label="t('button.close')" icon="close" type="button" to="/owner/dashboard" outline
                                color="white" text-color="black" />
                        </div>
                    </div>
                </q-form>
            </q-tab-panel>
            <q-tab-panel name="business">

                <q-form class="q-gutter-md" @submit="saveBusiness" autocorrect="off" autocapitalize="off"
                    autocomplete="off" spellcheck="false">

                    <q-input filled v-model="business.name" :label="t('business.name')" :hint="t('business.name')"
                        lazy-rules :rules="[val => val && val.length > 0 || t('business.invalid')]" />

                    <q-input filled v-model="business.mst" :label="t('business.tax_code')"
                        :hint="t('business.tax_code')" />
                    <q-input filled v-model="business.email" :label="t('business.email')" :hint="t('business.email')" />
                    <q-input filled v-model="business.phone" :label="t('business.phone')" :hint="t('business.phone')"
                        lazy-rules :rules="[val => val && val.length > 0 || t('business.invalid')]" />
                    <q-input filled v-model="business.address" label="Địa chỉ" hint="Địa chỉ" />
                    <q-input filled v-model="business.bankName" label="Tên ngân hàng" hint="Tên ngân hàng" />
                    <q-input filled v-model="business.bankAccount" label="Tài khoản ngân hàng" hint="Tài khoản ngân hàng" />


                    <div class="row">
                        <div class="col q-gutter-md">
                            <q-btn :label="t('button.save')" icon="check" :loading="loading" type="submit"
                                color="primary" />
                            <q-btn :label="t('business.close')" icon="close" type="button" to="/owner/dashboard" outline
                                color="white" text-color="black" />
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
import { userStore } from '../../stores/user'
import pinia from '../../stores'
const _userStore = userStore(pinia())
const tab = ref('information')
const loading = ref(false)
import * as ui from '../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
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
    phone: '',
    address: '',
    bankName: '',
    bankAccount: ''
})

async function fetch() {
    try {
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
    catch {
        ui.error(t('error.unknown'))
    }
}

async function saveUser() {
    try {
        loading.value = true;
        await api.api.user.updateUser(String(user.value.id), user.value)
        _userStore.saveUserInfo({ id: user.value.id, username: user.value.username, email: user.value.email, phoneNumber: user.value.phoneNumber, roles: role.value, businessId: user.value.businessId })
        loading.value = false
        ui.success(t('success.save'))

    } catch {
        ui.error(t('error.unknown'))
    }
}

async function saveBusiness() {
    try {
        loading.value = true;
        console.log(business.value)
        await api.api.business.updateBusiness(business.value.id, {
            name: business.value.name,
            mst: business.value.mst, email: business.value.email, phone: business.value.phone,
            address: business.value.address, bankName: business.value.bankName, bankAccount: business.value.bankAccount
        })
        loading.value = false
        ui.success(t('success.save'))
    }
    catch {
        ui.error(t('error.unknown'))
    }
}
onMounted(async () => {
    await fetch()
})
</script>