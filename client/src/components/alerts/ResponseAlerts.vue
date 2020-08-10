<template>
    <v-fade-transition
            :hide-on-leave="true"
    >
        <v-container
                :fluid="true"
                :key="alerts.length"
        >
            <v-row

            >
                <v-col
                        v-for="alert in alerts"
                        :key="alert.id"
                >
                    <v-alert
                            shaped
                            dismissible
                            border="left"
                            elevation="5"
                            :type="alert.type"
                            v-on:input="removeAlert(alert.id)"
                    >
                        <label v-text="alert.text"/>
                    </v-alert>
                </v-col>
            </v-row>
        </v-container>
    </v-fade-transition>
</template>

<script>
    export default {
        name: "ResponseAlerts",

        props: {
            alerts: Array
        },

        methods: {
            removeAlert(id) {
                this.$emit("removeResponseAlert", id)
            },
        }
    }
</script>

<style scoped>

</style>
