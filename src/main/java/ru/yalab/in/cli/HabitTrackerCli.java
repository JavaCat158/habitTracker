package ru.yalab.in.cli;

import ru.yalab.entity.Habit;
import ru.yalab.entity.User;
import ru.yalab.entity.UserRole;
import ru.yalab.out.database.DataBaseTemplate;
import ru.yalab.out.persistence.JDBCHabitRepository;
import ru.yalab.out.persistence.JDBCUserRepository;
import ru.yalab.uselogic.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Класс HabitTrackerCli предоставляет интерфейс командной строки (CLI) для пользователей и администраторов
 * для взаимодействия с системой отслеживания привычек. Он позволяет пользователям регистрироваться, входить в систему,
 * создавать, редактировать и удалять привычки, а также отслеживать свой прогресс. Администраторы имеют дополнительные
 * привилегии для управления пользователями и привычками.
 */
public class HabitTrackerCli {
    private final RegisterUser registerUser;
    private final LoginUser loginUser;
    private final CreateHabit createHabit;
    private final GetAllUsers getAllUsers;
    private final BlockUsers blockUsers;
    private final DeleteUserProfile deleteUser;
    private final UpdateUserProfile updateUserProfile;
    private final ResetPassword resetPassword;
    private final GenerateHabitReport generateHabitReport;
    private final UpdateHabit updateHabit;
    private final DeleteHabit deleteHabit;
    private final MarkHabitAsCompleted markHabitAsCompleted;
    private final GetHabitStatistic getHabitStatistic;
    private final DeleteAllHabit deleteAllHabit;
    private final FindAllByEmail findAllByEmail;

    DataBaseTemplate dataBaseTemplate;

    private String currentUser;
    private UserRole userRole;
    private final static String KEY_ADMIN = "admin";

    public HabitTrackerCli() {
        this.dataBaseTemplate = new DataBaseTemplate();
        JDBCHabitRepository habitRepository = new JDBCHabitRepository(dataBaseTemplate);
        JDBCUserRepository userRepository = new JDBCUserRepository(dataBaseTemplate);


        registerUser = new RegisterUser(userRepository);
        loginUser = new LoginUser(userRepository);
        createHabit = new CreateHabit(habitRepository);
        getAllUsers = new GetAllUsers(userRepository);
        blockUsers = new BlockUsers(userRepository);
        deleteUser = new DeleteUserProfile(userRepository);
        updateUserProfile = new UpdateUserProfile(userRepository, habitRepository);
        resetPassword = new ResetPassword(userRepository);
        generateHabitReport = new GenerateHabitReport(habitRepository);
        updateHabit = new UpdateHabit(habitRepository);
        deleteHabit = new DeleteHabit(habitRepository);
        markHabitAsCompleted = new MarkHabitAsCompleted(habitRepository);
        getHabitStatistic = new GetHabitStatistic(habitRepository);
        deleteAllHabit = new DeleteAllHabit(habitRepository);
        findAllByEmail = new FindAllByEmail(habitRepository);
    }

    public void start() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в приложение для отслеживание привычек.");

        while (true) {
            System.out.println("1. Регистрация");
            System.out.println("2. Вход");
            System.out.println("3. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                register(scanner);
            } else if (choice == 2) {
                login(scanner);
            } else if (choice == 3) {
                System.out.println("Good bye!");
                System.exit(0);
            }
        }
    }

    private void register(Scanner scanner) throws SQLException {
        System.out.println("Введите имя: ");
        String name = scanner.nextLine();

        System.out.println("Введите email: ");
        String email = scanner.nextLine();

        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();

        System.out.println("Если вы являетесь Администратором введите ключ: ");
        String answer = scanner.nextLine();

        if (answer.equals(KEY_ADMIN)) {
            userRole = UserRole.ADMIN;
            System.out.println("Вы являетесь Администратором!");
        } else {
            userRole = UserRole.USER;
            System.out.println("Вы не являетесь Администратором!");
        }

        if (registerUser.execute(name, email, password, UserRole.USER)) {
            System.out.println("Регистрация успешна!");
        } else {
            System.out.println("Пользователь с таким email уже существует");
        }
    }

    private void login(Scanner scanner) throws SQLException {
        System.out.println("Введите email: ");
        String email = scanner.nextLine();
        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();

        loginUser.execute(email, password);
        currentUser = email;
        User user = loginUser.getUser(email);
        UserRole userRole = user.getRole();

        System.out.println("Вход успешен!");
        if (userRole == UserRole.ADMIN) {
            adminMenu(scanner);
        } else {
            userMenu(scanner);
        }
    System.out.println("Неверный email или пароль");
}

// admin menu
private void adminMenu(Scanner scanner) throws SQLException {
    while (true) {
        System.out.println("Menu Admin:");
        System.out.println("1. Показать всех пользователей и привычки");
        System.out.println("2. Заблокировать пользователя");
        System.out.println("3. Удалить пользователя");
        System.out.println("4. Разблокировать пользователя");
        System.out.println("5. Выход");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                showAllUsers();
                createHabit.getAllHabits();
                break;
            case 2:
                blockUsers(scanner);
                break;
            case 3:
                deleteUser(scanner);
                break;
            case 4:
                unBlockUser(scanner);
            case 5:
                start();
            default:
                System.out.println("Ваш выбор уникален)");
        }
    }
}


private void showAllUsers() throws SQLException {
    LinkedHashSet<User> users = getAllUsers.execute();
    System.out.println("Список всех пользователей: ");
    for (User user : users) {
        System.out.println(user.getEmail() + " Имя: " + user.getName()
                + ", Admin: " + user.getRole()
                + ", Заблокирован " + user.getStatus() + "\n");
    }
}

// unblock user
private void unBlockUser(Scanner scanner) throws SQLException {
    System.out.println("Введите email пользователя для разблокировки: ");
    String email = scanner.nextLine();

    if (blockUsers.execute(email)) {
        System.out.println("Пользователь " + email + " разблокирован");
    }
}

// block users
private void blockUsers(Scanner scanner) throws SQLException {
    System.out.println("Введите email пользователя для блокировки:");
    String email = scanner.nextLine();

    if (blockUsers.execute(email)) {
        System.out.println("Пользователь " + email + " заблокирован");
    } else {
        System.out.println("Пользователь с таким " + email + " не найден");
    }
}

// delete user admin menu
private void deleteUser(Scanner scanner) throws SQLException {
    System.out.println("Введите email пользователя для удаления:");
    long id = Long.parseLong(scanner.nextLine());

    if (deleteUser.deleteUser(currentUser)) {
        deleteAllHabit.deleteAllHabits(id);
        System.out.println("Пользователь " + currentUser + " УДАЛЕН!!!");
    } else {
        System.out.println("Пользователь с таким " + id + " НЕ НАЙДЕН!!!");
    }
}

private void userMenu(Scanner scanner) throws SQLException {
    while (true) {
        System.out.println("1. Создать привычку.");
        System.out.println("2. Изменить привычку.");
        System.out.println("3. Удалить привычку.");
        System.out.println("4. Редактировать профиль.");
        System.out.println("5. Удалить профиль.");
        System.out.println("6. Сбросить пароль.");
        System.out.println("7. Список всех привычек.");
        System.out.println("8. Ваша статистика.");
        System.out.println("9. Отметить привычку.");
        System.out.println("10. Посмотреть статистику привычки по дате.");
        System.out.println("11. Выйти.");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            createHabit(scanner);
        } else if (choice == 2) {
            editHabit(scanner);
        } else if (choice == 3) {
            deleteHabit(scanner);
        } else if (choice == 4) {
            editProfile(scanner);
        } else if (choice == 5) {
            deleteAccount(scanner);
        } else if (choice == 6) {
            resetPassword(scanner);
        } else if (choice == 7) {
            List<Habit> habits = findAllByEmail.findAllByEmail(currentUser);
            habits.forEach(System.out::println);
        } else if (choice == 8) {
            generateReport(currentUser);
        } else if (choice == 9) {
            markHabit(scanner);
        } else if (choice == 10) {
            viewHabitDateStatistics(scanner);
        } else if (choice == 11) {
            currentUser = null;
            break;
        }
    }
}

private void createHabit(Scanner scanner) throws SQLException {
    System.out.println("Введите название привычки: ");
    String habitName = scanner.nextLine();
    System.out.println("Введите описание привычки: ");
    String habitDescription = scanner.nextLine();
    System.out.println("Введите частоту (ежедневно / еженедельно): ");
    String frequency = scanner.nextLine();

    createHabit.execute(habitName, habitDescription, frequency, currentUser);
    System.out.println("Привычка успешна создана!");
}

// опционально
private void resetPassword(Scanner scanner) throws SQLException {
    System.out.println("Введите ваш email для сброса пароля: ");
    String email = scanner.nextLine();

    if (resetPassword.execute(email)) {
        System.out.println("Временный пароль отправлен к вам на почту.");
    } else {
        System.out.println("Пользователь с таким email не найден");
    }
}

private void editProfile(Scanner scanner) throws SQLException {
    System.out.println("Введите ваше текущий email:");
    String currentName = scanner.nextLine();
    System.out.println("Введите новое имя: ");
    String email = scanner.nextLine();
    System.out.println("Введите новый email: ");
    String name = scanner.nextLine();
    System.out.println("Введите новый пароль: ");
    String password = scanner.nextLine();

    if (updateUserProfile.execute(currentName, email, name, password)) {
        System.out.println("Проффиль успешно обновлен!!!");
    } else {
        System.out.println("Не удалось обновить профиль.");
    }
}

private void deleteAccount(Scanner scanner) {
    System.out.println("Вы уверены что хотите удалить свой аккаунт?(y/n):");
    String answer = scanner.nextLine();
    if (answer.equalsIgnoreCase("y")) {
        try {
            if (deleteUser.deleteUser(currentUser)) {
                System.out.println("Аккаунт удален успешно");
                currentUser = null;
                start();
            } else {
                System.out.println("Ошибка удаления аккаунта.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

private void generateReport(String email) throws SQLException {
    generateHabitReport.execute(email);
}

private void editHabit(Scanner scanner) throws SQLException {
    System.out.println("Введите название привычки, которую хотите изменить: ");
    String oldTitle = scanner.nextLine();

    System.out.println("Введите новое название привычки: ");
    String newTitle = scanner.nextLine();

    System.out.println("Введите новое описание привычки: ");
    String newDescription = scanner.nextLine();

    System.out.println("Как часто вы будете ее выполнять(ежедневно / еженедельно): ");
    String newFrequency = scanner.nextLine();

    Habit habit = new Habit(0, newTitle, newDescription, newFrequency, currentUser);
    if (updateHabit.execute(habit)) {
        System.out.println("Привычка изменнена");
    } else {
        System.out.println("Не удалось найти привычку с таким названием.");
    }
}

private void deleteHabit(Scanner scanner) throws SQLException {
    findAllByEmail.findAllByEmail(currentUser).forEach(System.out::println);
    System.out.println("Введите номер привычки: ");
    long id = Long.parseLong(scanner.nextLine());

    if (deleteHabit.execute(currentUser, id)) {
        System.out.println("Привычка '" + id + "' успешно удалена!");
    } else {
        System.out.println("Не удалось удалить привычку!");
    }
}

private void markHabit(Scanner scanner) throws SQLException {
    System.out.println("Введите название привычки для отметки выполнения: ");
    long id = Long.parseLong(scanner.nextLine());

    if (markHabitAsCompleted.execute(currentUser, id)) {
        System.out.println("Привычка '" + id + "' успешно отмеченная как выполнена!");
    } else {
        System.out.println("Не удалось отметить привычку!");
    }
}

private void viewHabitDateStatistics(Scanner scanner) throws SQLException {
    System.out.println("Введите название привычки для просмотра статистики: ");
    Long id = Long.valueOf(scanner.nextLine());

    System.out.println("Введите дату начала (ГГГГ-ММ-ДД)");
    LocalDate startDate = LocalDate.parse(scanner.nextLine());

    System.out.println("Введите дату окончания (ГГГГ-ММ-ДД)");
    LocalDate endDate = LocalDate.parse(scanner.nextLine());

    int completedCount = getHabitStatistic.getCompletedCount(currentUser, id, startDate, endDate);
}
}
