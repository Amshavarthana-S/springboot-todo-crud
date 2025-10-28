// Shared script for login, register, and todos pages
const SERVER_URL = "http://localhost:8181";

// ===== LOGIN =====
function login() {
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if (!email || !password) {
        alert("Please enter both email and password.");
        return;
    }

    fetch(`${SERVER_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    })
    .then(res => res.json())
    .then(data => {
        if (data.token) {
            localStorage.setItem("token", data.token);
            alert("Login successful!");
            window.location.href = "todos.html";
        } else {
            alert(data.message || "Login failed!");
        }
    })
    .catch(err => {
        console.error(err);
        alert("Something went wrong. Check console for details.");
    });
}

// ===== REGISTER =====
function register() {
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if (!email || !password) {
        alert("Please enter both email and password.");
        return;
    }

    fetch(`${SERVER_URL}/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    })
    .then(res => res.text())
    .then(text => {
        alert(text);
        window.location.href = "login.html";
    })
    .catch(err => {
        console.error(err);
        alert("Something went wrong. Check console for details.");
    });
}

// ===== TODOS =====
function createTodoCard(todo) {
    const card = document.createElement("div");
    card.className = "todo-card";

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.checked = todo.isCompleted;
    checkbox.addEventListener("change", function() {
        const updatedTodo = { ...todo, isCompleted: checkbox.checked };
        updateTodoStatus(updatedTodo);
    });

    const span = document.createElement("span");
    span.textContent = todo.title;
    if (todo.isCompleted) {
        span.style.textDecoration = "line-through";
        span.style.color = "#aaa";
    }

    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "X";
    deleteBtn.onclick = function() {
        deleteTodo(todo.id);
    };

    card.appendChild(checkbox);
    card.appendChild(span);
    card.appendChild(deleteBtn);

    return card;
}

function loadTodos() {
    const token = localStorage.getItem("token");
    if (!token) {
        alert("Please login first");
        window.location.href = "login.html";
        return;
    }

    fetch(`${SERVER_URL}/api/v1/todo`, {
        method: "GET",
        headers: { "Authorization": `Bearer ${token}` }
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => { throw new Error(err.message || "Failed to get todo") });
        }
        return response.json();
    })
    .then(todos => {
        const todoList = document.getElementById("todo-list");
        todoList.innerHTML = "";
        if (!todos || todos.length === 0) {
            todoList.innerHTML = '<p id="empty-message">No Todos yet. Add one below!</p>';
        } else {
            todos.forEach(todo => todoList.appendChild(createTodoCard(todo)));
        }
    })
    .catch(error => {
        alert(error.message);
        const todoList = document.getElementById("todo-list");
        if (todoList) todoList.innerHTML = '<p style="color:red">Failed to load Todos!</p>';
    });
}

function addTodo() {
    const input = document.getElementById("new-todo");
    const todoText = input.value.trim();
    if (!todoText) return;

    const token = localStorage.getItem("token");

    fetch(`${SERVER_URL}/api/v1/todo/create`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({ title: todoText, isCompleted: false })
    })
    .then(res => {
        if (!res.ok) return res.text().then(text => { throw new Error(text || "Failed to add todo") });
        return res.text().then(text => text ? JSON.parse(text) : null);
    })
    .then(newTodo => {
        input.value = "";
        loadTodos();
    })
    .catch(error => {
        alert(error.message);
    });
}


function updateTodoStatus(todo) {
    const token = localStorage.getItem("token");

    fetch(`${SERVER_URL}/api/v1/todo`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(todo) // must include todo.id
    })
    .then(response => {
        if (!response.ok) throw new Error("Failed to update todo");
        loadTodos();
    })
    .catch(error => {
        alert(error.message);
    });
}


function deleteTodo(id) {
    const token = localStorage.getItem("token");

    fetch(`${SERVER_URL}/api/v1/todo/${id}`, {
        method: "DELETE",
        headers: { "Authorization": `Bearer ${token}` }
    })
    .then(response => {
        if (!response.ok) throw new Error("Failed to delete todo");
        loadTodos();
    })
    .catch(error => {
        alert(error.message);
    });
}

// Page-specific initialization
document.addEventListener("DOMContentLoaded", function () {
    if (document.getElementById("todo-list")) {
        loadTodos();
    }
});
