import {NavLink, useNavigate} from "react-router-dom";
import AuthService from "../services/AuthService.js";

const HeaderComponent = () => {

    const isAuth = AuthService.isUserLoggedIn();
    const navigator = useNavigate();

    function handleLogout() {
        AuthService.logout();
        navigator("/login");
        window.location.reload()
    }

    return (
        <div>
            <header>
                <nav className="navbar navbar-dark bg-dark navbar-expand-lg ">
                    <div className="container-fluid">
                        <a className="navbar-brand" href="/">Todo Management System</a>
                        <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                                data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false"
                                aria-label="Toggle navigation">
                            <span className="navbar-toggler-icon"></span>
                        </button>
                        <div className="collapse navbar-collapse" id="navbarNav">
                            {
                                isAuth &&
                                <ul className="navbar-nav">
                                    <li className="nav-item">
                                        <NavLink to="/todos" className="nav-link">Todos</NavLink>
                                    </li>
                                    <li className="nav-item">
                                        <NavLink to="/logout" onClick={(handleLogout)}
                                                 className="nav-link">Logout</NavLink>
                                    </li>
                                </ul>
                            }
                            <ul className="navbar-nav">
                                {
                                    !isAuth &&
                                    <>
                                        <li className="nav-item">
                                            <NavLink to="/register" className="nav-link">Register</NavLink>
                                        </li>
                                        <li className="nav-item">
                                            <NavLink to="/login" className="nav-link">Login</NavLink>
                                        </li>
                                    </>
                                }
                            </ul>
                        </div>
                    </div>
                </nav>
            </header>
        </div>
    );
}

export default HeaderComponent;