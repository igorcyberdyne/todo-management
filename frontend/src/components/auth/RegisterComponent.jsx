import {useState} from "react";
import AuthService from "../../services/AuthService.js";

const registerComponent = () => {

    const [name, setName] = useState("");
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [errors, setErrors] = useState({
        name: "",
        username: "",
        email: "",
        password: ""
    });


    function handleSubmit(e)
    {
        e.preventDefault();

        if (!isFormValid()) {
            return;
        }

        AuthService.register({name, username, email, password}).then((response) => {
            console.log(response.data)
        }).catch((error) => {
            console.log(error)
        });

    }

    function isFormValid()
    {
        let valid = true;

        const
            errorsCopy = {...errors},
            register = {name, username, email, password}
        ;

        ["name", "username", "email", "password"].forEach((field) => {
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
                    <h2>Register</h2>
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-2">
                                <label htmlFor="name">Name</label>
                                <input id="name"
                                       name="name"
                                       type="text"
                                       placeholder="Name"
                                       className={`form-control ${errors.name ? "is-invalid" : ""}`}
                                       onChange={(e) => setName(e.target.value)}
                                />
                                {errors.name && <div className="invalid-feedback">{errors.name}</div>}
                            </div>
                            <div className="form-group mb-2">
                                <label htmlFor="username">Username</label>
                                <input id="username"
                                       name="username"
                                       type="text"
                                       placeholder="Username"
                                       className={`form-control ${errors.username ? "is-invalid" : ""}`}
                                       onChange={(e) => setUsername(e.target.value)}
                                />
                                {errors.username && <div className="invalid-feedback">{errors.username}</div>}
                            </div>
                            <div className="form-group mb-2">
                                <label htmlFor="email">Email</label>
                                <input id="email"
                                       name="email"
                                       type="email"
                                       placeholder="Email"
                                       className={`form-control ${errors.email ? "is-invalid" : ""}`}
                                       onChange={(e) => setEmail(e.target.value)}
                                />
                                {errors.email && <div className="invalid-feedback">{errors.email}</div>}
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
                            <button type="submit" name="submit" onClick={handleSubmit} className="btn btn-success">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default registerComponent;