<template>
    <q-page class="row items-center justify-evenly">
        <q-card>
            <q-card-section>
                <div class="text-h6">{{ t('login.resetPassword') }}</div>
            </q-card-section>
            <q-card-section>
                <q-form @submit="onSubmit" class="q-gutter-md">
                    <q-input filled v-model="newPassword" :label="t('login.newPassword')" :hint="t('login.newPassword')"
                        type="password" required />
                    <q-input filled v-model="newPasswordConfirm" :label="t('login.newPasswordConfirm')"
                        :hint="t('login.newPasswordConfirm')" type="password" required />


                    <div>
                        <q-btn :label="t('login.resetPassword')" type="submit" :loading="loading" color="primary" />
                        <q-btn :label="t('button.close')" icon="close" type="button" to="/login" outline
                            color="grey-9" />
                    </div>
                </q-form>
            </q-card-section>
        </q-card>

    </q-page>
</template>
<script setup lang="ts">
import api, { ResetPassword } from '../services/api';
import { onMounted, ref } from 'vue'
import * as ui from '../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
const newPassword = ref('')
const newPasswordConfirm = ref('')
const loading = ref(false)
const reset_password = ref<ResetPassword>({
    verification_id: '',
    newPassword: '',
    token: ''
})

async function onSubmit() {
    loading.value = true
    try {
        if (newPassword.value !== newPasswordConfirm.value) {
            ui.error(t('error.password_mismatch'))
            return
        }
        reset_password.value.newPassword = newPassword.value
        await api.api.auth.resetPassword(reset_password.value)
        ui.success(t('success.reset_password'))
        loading.value = false
        window.location.href = '/login'
    } catch (error) {
        ui.error(t('error.unknown'))
    } finally {
        loading.value = false
    }
}
onMounted(async () => {
    const params = new URLSearchParams(window.location.search)
    const verification_id = params.get('verification_id')
    console.log('verification_id', verification_id)
    const token = params.get('token')

    // This is just a helper
    try {
        loading.value = true
        const res = await api.api.auth.getToken(verification_id, token)
        if (!res) {
            ui.error(t('error.invalid_token'))
            return
        }
        reset_password.value.verification_id = verification_id
        reset_password.value.token = token
    }
    catch (error) {
        ui.error(t('error.invalid_token'))
    }
    finally {
        loading.value = false
    }

})
</script>