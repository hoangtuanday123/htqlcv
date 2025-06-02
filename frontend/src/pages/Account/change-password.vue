<template>
    <q-page class="q-pa-md">
        <h1>Change Password</h1>
        <q-form class="q-gutter-md" @submit="save" autocorrect="off" autocapitalize="off" autocomplete="off"
            spellcheck="false">

            <q-input type="password" v-model="changePassword.oldPassword" label="Old Password" required />

            <q-input type="password" v-model="changePassword.newPassword" label="New Password" required />

            <q-input type="password" v-model="changePassword.confirmPassword" label="Confirm Password" required />

            <div class="row">
                <div class="col q-gutter-md">
                    <q-btn label="Save" icon="check" :loading="loading" type="submit" color="primary" />
                    <q-btn label="Close" icon="close" type="button" to="/account" outline color="white"
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
        ui.success('save successfull')
    }
    catch {
        ui.error('unknown')
    }
}


</script>