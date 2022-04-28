package org.incubyte.todo;

import jakarta.inject.Singleton;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Singleton
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {

        this.todoRepository = todoRepository;
    }

    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    public Optional<Todo> get(long id) {
        return todoRepository.findById(id);
    }

    public Iterable<Todo> getAllTodos() {
        return todoRepository.findAllOrderById();
    }

    public Iterable<Todo> getAllTodosByFilter(String status) {
        Iterable<Todo> todos = getAllTodos();
        boolean isDoneCondition = false;
        if (status == "Close") {
            isDoneCondition = true;
        }

        boolean finalIsDoneCondition = isDoneCondition;
        List<Todo> result = StreamSupport.stream(todos.spliterator(), false)
                .filter(todo -> todo.isDone() == finalIsDoneCondition)
                .collect(Collectors.toList());
        return result;
    }
}
