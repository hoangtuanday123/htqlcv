<template>
    <q-page class="q-pa-md">
        <h1>{{ t('account.change_password') }}</h1>
        <q-form class="q-gutter-md" @submit="save" autocorrect="off" autocapitalize="off" autocomplete="off"
            spellcheck="false">

            <q-input type="password" v-model="changePassword.oldPassword" :label="t('account.old_password')" required />

            <q-input type="password" v-model="changePassword.newPassword" :label="t('account.new_password')" required />

            <q-input type="password" v-model="changePassword.confirmPassword" :label="t('account.confirm_password')"
                required />

            <div class="row">
                <div class="col q-gutter-md">
                    <q-btn :label="t('button.save')" icon="check" :loading="loading" type="submit" color="primary" />
                    <q-btn :label="t('button.close')" icon="close" type="button" to="/account" outline color="white"
                        text-color="black" />
                </div>
            </div>
        </q-form>
    </q-page>
</template>
<script setup lang="ts">
import { ref } from 'vue';
import api, { ChangePassword } from '../../services/api';

import * as ui from '../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
const loading = ref(false)
const changePassword = ref<ChangePassword>({
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
})


async function save() {
    try {
        loading.value = true
        await api.api.user.ChangePassword(changePassword.value)
        loading.value = false
        ui.success(t('success.save'))
    }
    catch {
        ui.error(t('error.unknown'))
    }
}


</script>