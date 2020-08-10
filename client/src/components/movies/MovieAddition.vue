<template>
    <v-container
            :fluid="true"
    >
        <v-text-field
                v-model="title"
                :error-messages="titleErrors"
                label="Название фильма"
                required
                @input="$v.title.$touch()"
                @blur="$v.title.$touch()"
        />
        <v-text-field
                v-model="description"
                :error-messages="descriptionErrors"
                label="Описание"
                required
                @input="$v.description.$touch()"
                @blur="$v.description.$touch()"
        />
        <v-text-field
                v-model="producer"
                :error-messages="producerErrors"
                label="Режиссёр"
                required
                @input="$v.producer.$touch()"
                @blur="$v.producer.$touch()"
        />
        <v-select
                v-model="category"
                :items="categories"
                :error-messages="categoryErrors"
                label="Категория"
                required
                @change="$v.category.$touch()"
                @blur="$v.category.$touch()"
        />
        <v-text-field
                v-model="year"
                :error-messages="yearErrors"
                label="Год выпуска"
                required
                @input="$v.year.$touch()"
                @blur="$v.year.$touch()"
        />
        <v-btn
                class="mr-4"
                @click="submit"
                :disabled="$v.$invalid"
        >
            Добавить фильм
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
    import {required, integer, maxValue, minLength, maxLength} from 'vuelidate/lib/validators'
    import ResponseAlerts from "@/components/alerts/ResponseAlerts";

    export default {
        name: "movie-addition",

        components: {ResponseAlerts},

        created() {
            this.getCategoriesList();
        },

        mixins: [validationMixin],

        validations: {
            title: {required},
            description: {required},
            producer: {required},
            category: {required},
            year: {required, integer, minLength: minLength(4), maxLength: maxLength(4), maxValue: maxValue(2020)}
        },

        data() {
            return {
                title: '',
                description: '',
                producer: '',
                category: null,
                categories: [],
                year: '',
                alerts: []
            }
        },

        computed: {
            titleErrors() {
                const errors = []
                if (!this.$v.title.$dirty) return errors
                !this.$v.title.required && errors.push('Необходимо указать название фильма.')
                return errors
            }
            ,
            descriptionErrors() {
                const errors = []
                if (!this.$v.description.$dirty) return errors
                !this.$v.description.required && errors.push('Необходимо заполнить описание фильма.')
                return errors
            }
            ,
            producerErrors() {
                const errors = []
                if (!this.$v.producer.$dirty) return errors
                !this.$v.producer.required && errors.push('Необходимо указать режиссёра.')
                return errors
            }
            ,
            categoryErrors() {
                const errors = []
                if (!this.$v.category.$dirty) return errors
                !this.$v.category.required && errors.push('Необходимо указать категорию.')
                return errors
            }
            ,
            yearErrors() {
                const errors = []
                if (!this.$v.year.$dirty) return errors
                !this.$v.year.required && errors.push('Необходимо указать год выпуска фильма.')
                !this.$v.year.integer && errors.push('Необходимо ввести цифры.')
                !this.$v.year.maxLength && errors.push('Необходимо ввести 4 цифры.')
                !this.$v.year.minLength && errors.push('Необходимо ввести 4 цифры.')
                !this.$v.year.maxValue && errors.push('Невозможно указать будущий год.')
                return errors
            },

        },

        methods: {
            submit() {
                const vm = this;

                const xmlHttpRequest = new XMLHttpRequest();

                xmlHttpRequest.open("POST", "https://localhost:45091/movies/add", true);

                xmlHttpRequest.setRequestHeader("Content-type", "application/json");

                const request = {
                    MovieAdditionRequest: {
                        title: {
                            value: vm.title
                        },
                        year: vm.year,
                        producer: {
                            value: vm.producer
                        },
                        description: {
                            value: vm.description
                        },
                        category: {
                            value: vm.category
                        }
                    }
                };

                xmlHttpRequest.send(JSON.stringify(request));

                xmlHttpRequest.onload = function () {
                    const response = JSON.parse(this.response).MovieAdditionResponse;

                    if (response.result === "SUCCESS") {
                        vm.clear();
                        vm.addAlert("success", response.description.value)
                    } else if (response.result === "ERROR") {
                        vm.addAlert("error", response.description.value)
                    }
                }
            },

            clear() {
                this.$v.$reset()
                this.title = ''
                this.producer = ''
                this.category = null
                this.year = ''
                this.description = ''
            },

            getCategoriesList: function () {
                const vm = this;

                const xmlHttpRequest = new XMLHttpRequest();

                xmlHttpRequest.open("POST", "https://localhost:45091/categories/list", true);

                xmlHttpRequest.setRequestHeader("Content-type", "application/json");

                const request = {
                    CategoriesListRequest: {}
                };

                xmlHttpRequest.send(JSON.stringify(request));

                xmlHttpRequest.onload = function () {
                    const categoriesListResponse = JSON.parse(this.response).CategoriesListResponse;

                    for (const categoryId in categoriesListResponse.categories.map) {
                        vm.categories.push(categoriesListResponse.categories.map[categoryId].title.value);
                    }

                    if (vm.categories.length < 1) {
                        vm.addAlert("warning", "В базе нет категорий. Добавление фильма невозможно")
                    }
                }

                xmlHttpRequest.onerror = function () {
                    vm.addAlert("warning", "Не получается найти категории. Добавление фильма невозможно")
                    console.log("Cannot load all categories");
                }
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
