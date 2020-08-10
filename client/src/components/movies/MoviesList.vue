<template>
    <v-container
            fluid
    >
        <v-fade-transition>
            <v-row>
                <v-col
                        v-for="card in cards"
                        :key="card.id"
                        :title="card.title"
                >
                    <v-card
                            max-width="400"
                            min-width="300"
                            elevation="5"
                    >
                        <v-card-title
                                v-text="card.title"
                        />
                        <v-card-text
                                v-text="card.description"
                        />
                        <v-card-text
                                v-text="'Режиссёр: ' + card.producer"
                        />
                        <v-card-text
                                v-text="'Категория: ' + card.category"
                        />
                        <v-card-text
                                v-text="'Год: ' + card.year"
                        />
                        <v-card-actions>
                            <v-spacer/>
                            <v-btn
                                    icon
                                    @click="deleteMovie(card.id)"
                            >
                                <v-icon>mdi-delete</v-icon>
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
            </v-row>
        </v-fade-transition>
        <ResponseAlerts
                v-bind:alerts="alerts"
                v-on:removeResponseAlert="removeResponseAlert"
        />
    </v-container>
</template>

<script>
    import ResponseAlerts from "@/components/alerts/ResponseAlerts";

    export default {
        name: "movies-list",
        components: {ResponseAlerts},
        data() {
            return {
                cards: [],
                alerts: []
            }
        },

        created() {
            const vm = this;
            vm.getMoviesList();
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

            getMoviesList: function () {
                const vm = this;

                const xmlHttpRequest = new XMLHttpRequest();

                xmlHttpRequest.open("POST", "https://localhost:45091/movies/list", true);

                xmlHttpRequest.setRequestHeader("Content-type", "application/json");

                const request = {
                    MoviesListRequest: {}
                };

                xmlHttpRequest.send(JSON.stringify(request));

                xmlHttpRequest.onload = function () {
                    try {
                        const movies = JSON.parse(this.response).MoviesListResponse.movies;

                        for (const movieId in movies.map) {
                            const card = {
                                id: movies.map[movieId].id.value,
                                title: movies.map[movieId].title.value,
                                description: movies.map[movieId].description.value,
                                producer: movies.map[movieId].producer.value,
                                category: movies.map[movieId].category.value,
                                year: movies.map[movieId].year
                            };

                            vm.cards.push(card);


                        }

                        if (vm.cards.length < 1) {
                            vm.addAlert("warning", "В базе нет фильмов")
                        }
                    } catch (e) {
                        console.log(e)
                        vm.addAlert("error", "Ошибка при получении списка фильмов")
                    }
                }

                xmlHttpRequest.onerror = function () {
                    vm.addAlert("error", "Ошибка при получении списка фильмов")
                }
            },

            deleteMovie(id) {
                const vm = this;

                const xmlHttpRequest = new XMLHttpRequest();

                xmlHttpRequest.open("POST", "https://localhost:45091/movies/delete", true);

                xmlHttpRequest.setRequestHeader("Content-type", "application/json");

                const request = {
                    MovieDeletionRequest: {
                        id: {
                            value: id
                        }
                    }
                };

                xmlHttpRequest.send(JSON.stringify(request));

                xmlHttpRequest.onload = function () {
                    const response = JSON.parse(this.response).MovieDeletionResponse;

                    if (response.result === "SUCCESS") {
                        vm.resetCards();
                        vm.getMoviesList();
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
