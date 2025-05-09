<template>
    <div class="q-pa-md">
      <q-layout view="hHh Lpr lff">
        <q-header shadow-1 class="shadow-1 bg-primary text-white">
        <div class="container">
            <header-toolbar @toggleDrawer="toggleLeftDrawer"></header-toolbar>
        </div>
      </q-header>
  
        <q-page-container>
          <router-view />
        </q-page-container>
      </q-layout>
    </div>
</template>
  
<script setup lang="ts">
  import HeaderToolbar from './menus/header-toolbar.vue'
  import { ref } from 'vue'

  const leftDrawerOpen = ref(false)

  function toggleLeftDrawer() {
    leftDrawerOpen.value = !leftDrawerOpen.value
  }
</script>