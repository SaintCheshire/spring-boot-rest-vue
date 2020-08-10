<template>
    <v-container
            :fluid="true"
    >
        <v-text-field
                v-model="category"
                :error-messages="categoryErrors"
                label="Название категории"
                required
                @input="$v.category.$touch()"
                @blur="$v.category.$touch()"
                prepend-icon="mdi-text"
        />
        <v-text-field
                v-model="description"
                :error-messages="descriptionErrors"
                label="Описание категории"
                required
                @input="$v.description.$touch()"
                @blur="$v.description.$touch()"
                prepend-icon="mdi-text"
        />

        <v-btn
                class="mr-4"
                @click="submit"
                :disabled="$v.$invalid"
        >
            Добавить категорию
        </v-btn>
        <v-btn @click="clear">
            Очистить
        </v-btn>
        <ResponseAlerts
                v-bind:alerts="alerts"
                v-on:removeResponseAlert="removeResponseAlert"
        />
    </v-container>
</template>

<script>
    import {validationMixin} from 'vuelidate'
    import {required} from 'vuelidate/lib/validators'
    import ResponseAlerts from "@/components/alerts/ResponseAlerts";

    export default {
        name: "category-addition",
        components: {ResponseAlerts},
        mixins: [validationMixin],

        validations: {
            category: {required},
            description: {required}
        },

        data() {
            return {
                category: null,
                description: null,
                alerts: []
            };
        },

        computed: {
            categoryErrors() {
                const errors = []
                if (!this.$v.category.$dirty) return errors
                !this.$v.category.required && errors.push('Необходимо указать название категории.')
                return errors
            },

            descriptionErrors() {
                const errors = []
                if (!this.$v.description.$dirty) return errors
                !this.$v.description.required && errors.push('Необходимо указать описание категории.')
                return errors
            }
        },

        methods: {
            submit() {
                const vm = this;

                const xmlHttpRequest = new XMLHttpRequest();

                xmlHttpRequest.open("POST", "https://localhost:45091/categories/add", true);

                xmlHttpRequest.setRequestHeader("Content-type", "application/json");

                const request = {
                    CategoryAdditionRequest: {
                        title: {
                            value: vm.category,
                        },
                        description: {
                            value: vm.description
                        }
                    }
                };

                xmlHttpRequest.send(JSON.stringify(request));

                xmlHttpRequest.onload = function () {
                    const response = JSON.parse(this.response).CategoryAdditionResponse;

                    if (response.result === "SUCCESS") {
                        vm.clear();
                        vm.addAlert("success", response.description.value)
                    } else if (response.result === "WARNING") {
                        vm.addAlert("warning", response.description.value)
                    } else if (response.result === "ERROR") {
                        vm.addAlert("error", response.description.value)
                    }
                }
            },

            clear() {
                const vm = this;

                vm.$v.$reset();
                vm.category = '';
                vm.description = '';
            },

            addAlert(type, text) {
                const vm = this;

                const id = Math.max(...vm.alerts.map(o => o.id), 0) + 1;

                vm.alerts.push({id: id, type: type, text: text})
            },

            removeResponseAlert(id) {
                const vm = this;

                vm.alerts.splice(vm.alerts.findIndex(value => value.id === id), 1);
            }
        }
    }
</script>

<style scoped>

</style>
