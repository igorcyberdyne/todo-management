import HttpClient from "../http/HttpClient.js";


const httpClientInstance = HttpClient.create({
    baseURL: HttpClient.getUri() + "/auth"
})


class AuthService {
    static ROLE_ADMIN = "ROLE_ADMIN";

    static register = (register) => {
        return httpClientInstance.post("/register", register);
    }

    static login(login) {
        return httpClientInstance.post("/login", login);
    }

    static storeToken(token) {
        localStorage.setItem("token", token)
    }

    static getToken() {
        return localStorage.getItem("token")
    }

    static saveLoggedInUser(username, role) {
        sessionStorage.setItem("authenticatedUser", username)
        sessionStorage.setItem("role", role)
    }

    static isUserLoggedIn() {
        return sessionStorage.getItem("authenticatedUser") !== null;
    }

    static getLoggedInUser() {
        return sessionStorage.getItem("authenticatedUser")
    }

    static logout() {
        localStorage.clear();
        sessionStorage.clear();
    }

    static isAdminUser() {
        let role = sessionStorage.getItem("role");

        return role !== null && role === this.ROLE_ADMIN;
    }

    static isUserRoleEqualTo(role) {
        return role === sessionStorage.getItem("role");
    }
}

export default AuthService;