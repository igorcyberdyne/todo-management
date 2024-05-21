import {useEffect, useState} from "react";
import TodoService from "../../services/TodoService.js";
import {useNavigate} from "react-router-dom";
import AuthService from "../../services/AuthService.js";

const ListTodoComponent = () => {

    const [todos, setTodos] = useState([]);
    const navigator = useNavigate();

    const isAdminUser = AuthService.isAdminUser();


    useEffect(() => {
        getAllTodos();
    }, []);

    function getAllTodos() {
        TodoService.getAllTodos().then((res) => {
            setTodos(res.data);
        }).catch((error) => {
            console.log(error);
        });
    }

    function addNewTodo() {
        navigator("/add-todo");
    }

    function handleUpdateAction(todoId) {
        navigator(`/edit-todo/${todoId}`);
    }

    function handleDeleteAction(todoId) {
        TodoService.deleteTodo(todoId).then(() => {
            getAllTodos();
        }).catch(error => {
            console.log(error);
        });
    }

    function handleCompleteAction(todoId) {
        TodoService.completeTodo(todoId).then(() => {
            getAllTodos();
        }).catch(error => {
            console.log(error);
        });
    }

    function handleInCompleteAction(todoId) {
        TodoService.inCompleteTodo(todoId).then(() => {
            getAllTodos();
        }).catch(error => {
            console.log(error);
        });
    }

    return (
        <div className="container">
            <div className="col-md-8 offset-2">
                <h2>List of Todos</h2>
                {
                    isAdminUser && <button className="btn btn-primary mb-2" onClick={addNewTodo}>Add todo</button>
                }
                <table className="table table-striped table-bordered">
                    <thead className="thead">
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Completed</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {todos.map((todo) => (
                        <tr key={todo.id}>
                            <td>{todo.id}</td>
                            <td>{todo.title}</td>
                            <td>{todo.description}</td>
                            <td>{todo.completed ? "Yes" : "No"}</td>
                            <td>
                                {
                                    isAdminUser
                                    &&
                                    <>
                                        <button onClick={() => {
                                            handleUpdateAction(todo.id)
                                        }} className="btn btn-info">Update
                                        </button>
                                        &nbsp;&nbsp;
                                        <button onClick={() => {
                                            handleDeleteAction(todo.id)
                                        }} className="btn btn-danger">Delete
                                        </button>
                                        &nbsp;&nbsp;
                                    </>
                                }

                                <button onClick={() => {
                                    handleCompleteAction(todo.id)
                                }} className="btn btn-outline-success">Complete
                                </button>
                                &nbsp;&nbsp;
                                <button onClick={() => {
                                    handleInCompleteAction(todo.id)
                                }} className="btn btn-warning">In complete
                                </button>
                                &nbsp;&nbsp;

                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default ListTodoComponent;