<template>
    <q-page class="row items-center justify-evenly">
        <q-card>
            <q-card-section>
                <div class="text-h6">{{ t('login.forgotPassword') }}</div>
            </q-card-section>
            <q-card-section>
                <q-form @submit="onSubmit" class="q-gutter-md">
                    <q-input filled v-model="email" :label="t('login.email')" :hint="t('login.email')" required />


                    <div>
                        <q-btn :label="t('login.forgotPassword')" type="submit" :loading="loading" color="primary" />
                        <q-btn :label="t('button.close')" icon="close" type="button" to="/login" outline
                            color="grey-9" />
                    </div>
                </q-form>
            </q-card-section>

        </q-card>
    </q-page>
</template>

<script setup lang="ts">
import api from '../services/api';
import { ref } from 'vue'
import * as ui from '../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
const email = ref('')
const loading = ref(false)
async function onSubmit() {
    loading.value = true
    try {
        // console.log('Forgot password request for:', email.value);
        await api.api.auth.forgotPassword(email.value);
        ui.success(t('success.forgot_password'));
    } catch (error) {
        ui.error(t('error.unknown'))
    }
    finally {

        loading.value = false
    }
}
</script>