<template>
    <q-page class="q-pa-md">
        <h1>{{ t('customer.create') }}</h1>
        <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
            spellcheck="false">
            <q-input v-model="customer.name" :label="t('customer.name')" required />
            <q-input v-model="customer.phone" :label="t('customer.phone')" required />
            <q-input filled v-model="customer.dob" mask="####-##-##"
                :rules="[val => /^\d{4}-\d{2}-\d{2}$/.test(val) || t('customer.invalid_dob')]">
                <template v-slot:append>
                    <q-icon name="event" class="cursor-pointer">
                        <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                            <q-date v-model="customer.dob" mask="YYYY-MM-DD" format="YYYY-MM-DD">
                                <div class="row items-center justify-end">
                                    <q-btn v-close-popup label="Close" color="primary" flat />
                                </div>
                            </q-date>
                        </q-popup-proxy>
                    </q-icon>
                </template>
            </q-input>

            <q-input v-model="customer.address" :label="t('customer.address')" />
            <div class="q-gutter-sm">
                <q-radio v-model="customer.customerType" val="individual" :label="t('customer.individual')" />
                <q-radio v-model="customer.customerType" val="companyCustomer"
                    :label="t('customer.company_customer')" />
            </div>
            <q-input v-model="customer.mst" :label="t('customer.tax_code')" />
            <q-input v-model="customer.cmnd" :label="t('customer.personal_code')" />
            <q-input v-model="customer.email" :label="t('customer.email')" />
            <q-input v-model="customer.mst" :label="t('customer.note')" />
            <div v-if="customer.customerType === 'companyCustomer'">
                <q-input v-model="customer.companyName" :label="t('customer.company_name')" />

            </div>
            <div class="row">
                <div class="col q-gutter-md">
                    <q-btn :label="t('button.save')" icon="check" :loading="loading" type="submit" color="primary" />
                    <q-btn :label="t('button.close')" icon="close" type="button" to="../customers" outline
                        color="grey-9" />
                </div>
            </div>
        </q-form>
    </q-page>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import api, { CustomerRequest } from '../../../services/api'
import * as ui from '../../../utils/ui'
const router = useRouter()
const loading = ref(false)
import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info

let customer: CustomerRequest = reactive({
    name: '',
    phone: '',
    dob: null as Date | null,
    address: '',
    customerType: 'individual',
    mst: '',
    cmnd: '',
    email: '',
    note: '',
    companyName: null,
    businessId: userInfo.value.businessId
})

async function save() {
    try {
        loading.value = true
        await api.api.customer.createCustomer(customer)
        loading.value = false
        ui.success(t('success.save'))
        router.push({ path: '../customers' })

    } catch {
        ui.error(t('error.unknown'))
    }
}
</script>