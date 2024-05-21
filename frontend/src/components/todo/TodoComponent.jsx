import {useEffect, useState} from "react";
import TodoService from "../../services/TodoService.js";
import {useNavigate, useParams} from "react-router-dom";

const TodoComponent = (props) => {

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [completed, setCompleted] = useState("");
    const navigator = useNavigate();
    const {id} = useParams();

    const [errors, setErrors] = useState({
        title: "",
        description: "",
        completed: ""
    });

    function handleTitle(e) {
        setTitle(e.target.value)
    }

    function handleDescription(e) {
        setDescription(e.target.value)
    }
    function handleCompleted(e) {
        setCompleted(e.target.value)
    }

    function handleSaveTodo(e) {
        e.preventDefault();
        const todo = {title, description, completed};

        if (!validateForm()) {
            return;
        }

        if (id) {
            TodoService.updateTodo(id, todo).then((res) => {
                navigator("/todos");
            }).catch(error => {
                console.log(error);
            });

            return;
        }

        TodoService.addNewTodo(todo).then((res) => {
            navigator("/todos");
        }).catch(error => {
            console.log(error);
        });
    }

    function validateForm() {
        let valid = true;

        const errorsCopy = {...errors};

        if (!title.trim()) {
            errorsCopy.title = "Title is required";
            valid = false;
        } else {
            errorsCopy.title = "";
        }

        if (!description.trim()) {
            errorsCopy.description = "Description is required";
            valid = false;
        } else {
            errorsCopy.description = "";
        }

        if (completed === "") {
            errorsCopy.completed = "Completed is required";
            valid = false;
        } else {
            errorsCopy.completed = "";
        }

        setErrors(errorsCopy);

        return valid;
    }

    function pageTitle() {
        if (id) {
            return "Edit Todo";
        }

        return "Add Todo";
    }

    useEffect(() => {
        if (!id) {
            return;
        }

        TodoService.getTodo(id).then((res) => {
            setTitle(res.data.title);
            setDescription(res.data.description);
            setCompleted(res.data.completed);
        }).catch(error => {
            console.log(error);
        });

    }, [id]);

    return (
        <div className="container">
            <div className="card col-md-8 offset-2">
                <h2>{pageTitle()}</h2>
                <div className="card-body">
                    <form>
                        <div className="form-group mb-2">
                            <label htmlFor="title">Title</label>
                            <input
                                id="title"
                                className={`form-control ${errors.title ? "is-invalid" : ""}`}
                                type="text"
                                placeholder="Title"
                                name="title"
                                value={title}
                                onChange={handleTitle}
                            />
                            {errors.title && <div className="invalid-feedback">{errors.title}</div>}
                        </div>
                        <div className="form-group mb-2">
                            <label htmlFor="description">Description</label>
                            <input
                                id="description"
                                className={`form-control ${errors.description ? "is-invalid" : ""}`}
                                type="text"
                                placeholder="Description"
                                name="description"
                                value={description}
                                onChange={handleDescription}
                            />
                            {errors.description && <div className="invalid-feedback">{errors.description}</div>}
                        </div>

                        <div className="form-group mb-2">
                            <label htmlFor="completed">Completed</label>
                            <select
                                id="completed"
                                className={`form-control ${errors.completed ? "is-invalid" : ""}`}
                                name="completed"
                                value={completed}
                                onChange={handleCompleted}
                            >
                                <option value="">-- Select a completed --</option>
                                <option value="false">No</option>
                                <option value="true">Yes</option>
                            </select>
                            {errors.completed && <div className="invalid-feedback">{errors.completed}</div>}
                        </div>

                        <button type="submit" className="btn btn-success" onClick={handleSaveTodo}>Submit</button>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default TodoComponent;