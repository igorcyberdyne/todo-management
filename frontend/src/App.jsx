import './App.css'
import ListTodoComponent from "./components/todo/ListTodoComponent.jsx";
import FooterComponent from "./components/FooterComponent.jsx";
import HeaderComponent from "./components/HeaderComponent.jsx";
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import TodoComponent from "./components/todo/TodoComponent.jsx";
import RegisterComponent from "./components/auth/RegisterComponent.jsx";
import LoginComponent from "./components/auth/LoginComponent.jsx";
import AuthService from "./services/AuthService.js";

function App() {

    function AuthenticatedRoute({children, requiredRole}) {

        const isAuth = AuthService.isUserLoggedIn();

        if (isAuth) {
            return !requiredRole || AuthService.isUserRoleEqualTo(requiredRole) ? children : <Navigate to="/"/>;
        }

        return <Navigate to="/login"/>
    }

    return (
        <>
            <BrowserRouter>
                <HeaderComponent/>
                <Routes>
                    <Route path="/" element={
                        <AuthenticatedRoute>
                            <ListTodoComponent/>
                        </AuthenticatedRoute>
                    }/>
                    <Route path="/todos" element={
                        <AuthenticatedRoute>
                            <ListTodoComponent/>
                        </AuthenticatedRoute>
                    }/>
                    <Route path="/add-todo" element={
                        <AuthenticatedRoute requiredRole={AuthService.ROLE_ADMIN}>
                            <TodoComponent/>
                        </AuthenticatedRoute>
                    }/>
                    <Route path="/edit-todo/:id" element={
                        <AuthenticatedRoute requiredRole={AuthService.ROLE_ADMIN}>
                            <TodoComponent/>
                        </AuthenticatedRoute>
                    }/>
                    <Route path="/register" element={<RegisterComponent/>}/>
                    <Route path="/login" element={<LoginComponent/>}/>
                </Routes>
                <FooterComponent/>
            </BrowserRouter>
        </>
    )


}

export default App
