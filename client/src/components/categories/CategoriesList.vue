<template>
    <v-container
            fluid
    >
        <v-row>
            <v-col
                    v-for="card in cards"
                    :key="card.title"
                    :title="card.title"
            >
                <v-card
                        max-width="400"
                        min-width="300"
                        elevation="5"
                >
                    <v-card-title
                            class="text-center"
                            v-text="card.title"
                    />
                    <v-card-text
                            class="text-center"
                            v-text="card.description"
                    />
                    <v-card-actions>
                        <v-spacer/>
                        <v-btn
                                icon
                                @click="deleteCategory(card.id)"
                        >
                            <v-icon>mdi-delete</v-icon>
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
        <ResponseAlerts
                v-bind:alerts="alerts"
                v-on:removeResponseAlert="removeResponseAlert"
        />
    </v-container>
</template>

<script>
    import ResponseAlerts from "@/components/alerts/ResponseAlerts";

    export default {
        name: "categories-list",
        components: {ResponseAlerts},
        data() {
            return {
                cards: [],
                alerts: []
            }
        },

        created() {
            const vm = this;

            vm.getAllCategories();
        },

        watch: {
            cards: function () {
            }
        },

        methods: {
            resetCards: function () {
                const vm = this;

                vm.cards = [];
            },

            getAllCategories: function () {
                const vm = this;

                const xmlHttpRequest = new XMLHttpRequest();

                xmlHttpRequest.open("POST", "https://localhost:45091/categories/list", true);

                xmlHttpRequest.setRequestHeader("Content-type", "application/json");

                const request = {
                    CategoriesListRequest: {}
                };

                xmlHttpRequest.send(JSON.stringify(request));

                xmlHttpRequest.onload = function () {
                    const categories = JSON.parse(this.response).CategoriesListResponse.categories;

                    for (const categoryId in categories.map) {
                        const card = {
                            id: categories.map[categoryId].id.value,
                            title: categories.map[categoryId].title.value,
                            description: categories.map[categoryId].description.value,
                        };

                        vm.cards.push(card);
                    }

                    if (vm.cards.length < 1) {
                        vm.addAlert("warning", "В базе нет категорий")
                    }
                }

                xmlHttpRequest.onerror = function () {
                    vm.addAlert("error", "Ошибка при получении списка категорий")
                }
            },

            deleteCategory(id) {
                const vm = this;

                const xmlHttpRequest = new XMLHttpRequest();

                xmlHttpRequest.open("POST", "https://localhost:45091/categories/delete", true);

                xmlHttpRequest.setRequestHeader("Content-type", "application/json");

                const request = {
                    CategoryDeletionRequest: {
                        id: {
                            value: id
                        }
                    }
                };

                xmlHttpRequest.send(JSON.stringify(request));

                xmlHttpRequest.onload = function () {
                    const response = JSON.parse(this.response).CategoryDeletionResponse;

                    if (response.result === "SUCCESS") {
                        vm.resetCards();
                        vm.getAllCategories();
                        vm.addAlert("success", response.description.value)
                    } else if (response.result === "ERROR") {
                        vm.addAlert("error", response.description.value)
                    }
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
