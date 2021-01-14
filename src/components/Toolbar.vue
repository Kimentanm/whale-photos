<template>
  <v-app-bar
    id="toolbar"
    dark
    app
  >
    <v-app-bar-nav-icon @click="showNavigation"></v-app-bar-nav-icon>
    <v-toolbar-title>Whale Photos</v-toolbar-title>

    <v-spacer></v-spacer>

    <v-text-field
      class="mx-4"
      flat
      hide-details
      label="搜索"
      prepend-inner-icon="mdi-magnify"
      solo-inverted
    ></v-text-field>
  </v-app-bar>
</template>

<script>
export default {
  name: 'Toolbar',
  components: {},
  props: {

  },
  data() {
    return {}
  },
  computed: {},
  watch: {},
  mounted() {
  },
  created() {
  },
  methods: {
    showNavigation() {
      this.$emit('navigation-show')
    }
  }
}
</script>

<style lang="less">
#toolbar {

  .v-input__control {
    min-height: 36px;
  }

  .logo-text {
    font-size: 30px;
    font-weight: bold;
  }
}
</style>
