<template>
    <q-page class="q-pa-md">
        <h1>{{t('supplier.create')}}</h1>
        <q-form @submit="save" class="q-gutter-md" autocorrect="off" autocapitalize="off" autocomplete="off"
            spellcheck="false">
            <q-input v-model="supplier.company" :label="t('supplier.company')" />
            <q-input v-model="supplier.name" :label="t('supplier.name')" required />
            <q-input v-model="supplier.phone" :label="t('supplier.phone')" required />
            <q-input v-model="supplier.address" :label="t('supplier.address')" />
            <q-input v-model="supplier.email" :label="t('supplier.email')" />
            <q-input v-model="supplier.branch" :label="t('supplier.branch')" />
            <q-input v-model="supplier.mst" :label="t('supplier.tax_code')" />
            <q-input v-model="supplier.note" :label="t('supplier.note')" />
            <div class="row">
                <div class="col q-gutter-md">
                    <q-btn :label="t('button.save')" icon="check" :loading="loading" type="submit" color="primary" />
                    <q-btn :label="t('button.close')" icon="close" type="button" to="../suppliers" outline color="grey-9" />
                </div>
            </div>
        </q-form>
    </q-page>
</template>
<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import * as ui from '../../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import api, { SupplierRequest } from '../../../services/api'
import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info
const router = useRouter()
const loading = ref(false)

let supplier: SupplierRequest = reactive({
    name: '',
    phone: '',
    address: '',
    email: '',
    note: '',
    branch: '',
    mst: '',
    company: '',
    businessId: userInfo.value.businessId
})

async function save() {
    try{
        loading.value = true
        await api.api.supplier.createSupplier(supplier)
        loading.value = false
        router.push({ path: '../suppliers' })
    } catch {
       ui.error(t('error.unknown'))
    }
}
</script>
