<template>
    <v-app>
        <v-navigation-drawer
                v-model="drawer"
                app
                clipped
        >
            <v-list>
                <v-list-group
                        v-for="item in items"
                        :key="item.title"
                        v-model="item.active"
                        :prepend-icon="item.icon"
                        no-action
                >
                    <template
                            v-slot:activator
                    >
                        <v-list-item-content>
                            <v-list-item-title
                                    v-text="item.title"
                            />
                        </v-list-item-content>
                    </template>
                    <v-list-item
                            v-for="subItem in item.items"
                            :key="subItem.title"
                            :to="subItem.to"
                    >
                        <v-list-item-content>
                            <v-list-item-title
                                    v-text="subItem.title"
                            />
                        </v-list-item-content>
                    </v-list-item>
                </v-list-group>
            </v-list>
        </v-navigation-drawer>
        <v-app-bar
                app
                clipped-left
        >
            <v-app-bar-nav-icon
                    @click.stop="drawer = !drawer"
            />
            <v-toolbar-title>
                The Movies
            </v-toolbar-title>
        </v-app-bar>
        <v-main>
            <router-view/>
        </v-main>
        <v-footer
                app
        >
            <span>
                Saint Cheshire &copy; 2020
            </span>
        </v-footer>
    </v-app>
</template>

<script>
    export default {
        name: "App",

        props: {
            source: String,
        },

        data() {
            return {
                drawer: null,
                items: [
                    {
                        icon: 'mdi-movie-open',
                        title: 'Фильмы',
                        active: true,
                        items: [
                            {
                                title: 'Список',
                                active: true,
                                to: "/movies/list"
                            },
                            {
                                title: 'Добавить',
                                active: true,
                                to: "/movies/add"
                            }
                        ]
                    },

                    {
                        icon: 'mdi-film',
                        title: 'Категории',
                        active: true,
                        items: [
                            {
                                title: 'Список',
                                active: true,
                                to: "/categories/list"
                            },
                            {
                                title: 'Добавить',
                                active: true,
                                to: "/categories/add"
                            }
                        ]
                    }
                ]
            }
        },

        created() {
            this.$vuetify.theme.light = true
        }
    }
</script>

<style>
</style>
