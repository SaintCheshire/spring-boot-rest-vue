import Vue from 'vue'
import VueRouter from "vue-router";
import CategoryAddition from "@/components/categories/CategoryAddition";
import CategoriesList from "@/components/categories/CategoriesList";
import MovieAddition from "@/components/movies/MovieAddition";
import MoviesList from "@/components/movies/MoviesList";

Vue.use(VueRouter)

const routes = {
    routes: [
        {
            name: "dashboard",
            path: "/",
        },
        {
            name: "movies",
            path: "/movies/list",
            component: MoviesList
        },
        {
            name: "add movie",
            path: "/movies/add",
            component: MovieAddition
        },
        {
            name: "categories",
            path: "/categories/list",
            component: CategoriesList
        },
        {
            name: "add category",
            path: "/categories/add",
            component: CategoryAddition
        },
    ]
}

export default new VueRouter(routes)
