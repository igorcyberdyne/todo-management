import HttpClient from "../http/HttpClient.js";
import AuthService from "./AuthService.js";


const httpClientInstance = HttpClient.create({
    baseURL: HttpClient.getUri() + "/todos"
})


httpClientInstance.interceptors.request.use(function (config) {
    // Do something before request is sent
    config.headers["Authorization"] = AuthService.getToken();
    return config;
}, function (error) {
    // Do something with request error
    return Promise.reject(error);
});


class TodoService {
    static getAllTodos = () => {
        return httpClientInstance.get("");
    }

    static addNewTodo(todo) {
        return httpClientInstance.post("", todo);
    }

    static updateTodo(todoId, todo) {
        return httpClientInstance.put("/" + todoId, todo);
    }

    static getTodo(todoId) {
        return httpClientInstance.get("/" + todoId);
    }

    static deleteTodo(todoId) {
        return httpClientInstance.delete("/" + todoId);
    }

    static completeTodo(todoId) {
        return httpClientInstance.patch("/" + todoId + "/complete");
    }

    static inCompleteTodo(todoId) {
        return httpClientInstance.patch("/" + todoId + "/in-complete");
    }
}

export default TodoService;