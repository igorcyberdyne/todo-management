import {useState} from "react";
import AuthService from "../../services/AuthService.js";
import {NavLink, useNavigate} from "react-router-dom";

const registerComponent = () => {

    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [errors, setErrors] = useState({
        login: "",
        password: ""
    });
    const navigator = useNavigate();


    function handleSubmit(e) {
        e.preventDefault();

        if (!isFormValid()) {
            return;
        }

        AuthService.login({usernameOrEmail: login, password: password}).then((response) => {

            const token = "Bearer " + response.data.accessToken;
            const role = response.data.role;

            AuthService.storeToken(token);
            AuthService.saveLoggedInUser(login, role);

            navigator("/todos");
        }).catch((error) => {
            console.log(error)
        });

    }

    function isFormValid() {
        let valid = true;

        const
            errorsCopy = {...errors},
            register = {login, password}
        ;

        ["login", "password"].forEach((field) => {
            if (!register[field].trim()) {
                errorsCopy[field] = field + " is required";
                valid = false;
            } else {
                errorsCopy[field] = "";
            }
        });

        setErrors(errorsCopy);

        return valid;
    }

    return (
        <div className="container">
            <div className="row">
                <div className="card col-md-8 offset-2 mt-2">
                    <h2>Login In</h2>
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-2">
                                <label htmlFor="login">Username or email</label>
                                <input id="login"
                                       name="login"
                                       type="text"
                                       placeholder="login"
                                       className={`form-control ${errors.login ? "is-invalid" : ""}`}
                                       onChange={(e) => setLogin(e.target.value)}
                                />
                                {errors.login && <div className="invalid-feedback">{errors.login}</div>}
                            </div>
                            <div className="form-group mb-2">
                                <label htmlFor="password">Password</label>
                                <input id="password"
                                       name="password"
                                       type="password"
                                       placeholder="Password"
                                       className={`form-control ${errors.password ? "is-invalid" : ""}`}
                                       onChange={(e) => setPassword(e.target.value)}
                                />
                                {errors.password && <div className="invalid-feedback">{errors.password}</div>}
                            </div>
                            <button
                                type="submit"
                                name="submit"
                                onClick={handleSubmit}
                                className="btn btn-success mb-2">Login
                            </button>
                            <br/>
                            <NavLink to="/register" className="nav-links">You do not have an account ? Register here
                                !</NavLink>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default registerComponent;