package org.example.in.cli;

import org.example.entity.Habit;
import org.example.entity.User;
import org.example.out.persistence.InMemoryHabitRepository;
import org.example.out.persistence.InMemoryUserRepository;
import org.example.uselogic.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**

 Класс HabitTrackerCli предоставляет интерфейс командной строки (CLI) для пользователей и администраторов
 для взаимодействия с системой отслеживания привычек. Он позволяет пользователям регистрироваться, входить в систему,
 создавать, редактировать и удалять привычки, а также отслеживать свой прогресс. Администраторы имеют дополнительные
 привилегии для управления пользователями и привычками.

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

    private String currentUser;
    private boolean isAdmin;
    private final static String KEY_ADMIN = "admin";

    public HabitTrackerCli() {
        InMemoryHabitRepository habitRepository = new InMemoryHabitRepository();
        InMemoryUserRepository userRepository = new InMemoryUserRepository();

        // Тестовые юзеры
        User user1 = new User("user1", "user1@test.ru", "pass1", false);
        User user2 = new User("user2", "user2@test.ru", "pass2", false);
        // admin
        User user3 = new User("user3", "user3@test.ru", "pass3", true);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // Создание тестовых привычек
        Habit habit1 = new Habit("Пить воду",
                "Пить 2 литра воды в день",
                "ежедневно",
                            user1.getEmail());
        Habit habit2 = new Habit("Чтение книг",
                "Читать 20 страниц книги каждый день",
                "ежедневно",
                user1.getEmail());
        Habit habit3 = new Habit
                ("Бег",
                        "Бегать по утрам",
                        "ежедневно",
                        user1.getEmail()
                );
        Habit habit4 = new Habit("Изучение языков",
                "Учить 10 новых слов в день",
                "ежедневно",
                user2.getEmail());

        // Добавление в память привычек user1@test.ru
        habitRepository.save(habit1);
        habitRepository.save(habit2);
        habitRepository.save(habit3);
        habitRepository.save(habit4);

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

    public void start() {
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

    private void register(Scanner scanner) {
        System.out.println("Введите имя: ");
        String name = scanner.nextLine();

        System.out.println("Введите email: ");
        String email = scanner.nextLine();

        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();

        System.out.println("Если вы являетесь Администратором введите ключ: ");
        String answer = scanner.nextLine();

        if (answer.equals(KEY_ADMIN)) {
            isAdmin = true;
            System.out.println("Вы являетесь Администратором!");
        } else {
            isAdmin = false;
            System.out.println("Вы не являетесь Администратором!");
        }

        if (registerUser.execute(name, email, password, isAdmin)) {
            System.out.println("Регистрация успешна!");
        } else {
            System.out.println("Пользователь с таким email уже существует");
        }
    }

    private void login(Scanner scanner) {
        System.out.println("Введите email: ");
        String email = scanner.nextLine();
        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();

        if (loginUser.execute(email, password)) {
            currentUser = email;
            User user = loginUser.getUser(email);
            isAdmin = user.isAdmin();

            System.out.println("Вход успешен!");
            if (isAdmin) {
                adminMenu(scanner);
            } else {
                userMenu(scanner);
            }
        } else {
            System.out.println("Неверный email или пароль");
        }
    }

    // admin menu
    private void adminMenu(Scanner scanner) {
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


    private void showAllUsers() {
        List<User> users = getAllUsers.execute();
        System.out.println("Список всех пользователей: ");
        for (User user : users) {
            System.out.println(user.getEmail() + " Имя: " + user.getName()
                    + ", Admin: " + user.isAdmin()
                    + ", Заблокирован " + user.isBloced() + "\n");
        }
    }

    // unblock user
    private void unBlockUser(Scanner scanner) {
        System.out.println("Введите email пользователя для разблокировки: ");
        String email = scanner.nextLine();

        if (blockUsers.execute(email)) {
            System.out.println("Пользователь " + email + " разблокирован");
        }
    }

    // block users
    private void blockUsers(Scanner scanner) {
        System.out.println("Введите email пользователя для блокировки:");
        String email = scanner.nextLine();

        if (blockUsers.execute(email)) {
            System.out.println("Пользователь " + email + " заблокирован");
        } else {
            System.out.println("Пользователь с таким " + email + " не найден");
        }
    }

    // delete user admin menu
    private void deleteUser(Scanner scanner) {
        System.out.println("Введите email пользователя для удаления:");
        String email = scanner.nextLine();

        if (deleteUser.deleteUser(email)) {
            deleteAllHabit.deleteAllHabits(email);
            System.out.println("Пользователь " + email + " УДАЛЕН!!!");
        } else {
            System.out.println("Пользователь с таким " + email + " НЕ НАЙДЕН!!!");
        }
    }

    private void userMenu(Scanner scanner) {
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
            }else if (choice == 5) {
                deleteAccount(scanner);
            } else if (choice == 6) {
                resetPassword(scanner);
            } else if (choice == 7) {
                List<Habit> habits = findAllByEmail.findAllByEmail(currentUser);
                habits.forEach(System.out::println);
            }else if (choice == 8) {
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

    private void createHabit(Scanner scanner) {
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
    private void resetPassword(Scanner scanner) {
        System.out.println("Введите ваш email для сброса пароля: ");
        String email = scanner.nextLine();

        if (resetPassword.execute(email)) {
            System.out.println("Временный пароль отправлен к вам на почту.");
        } else {
            System.out.println("Пользователь с таким email не найден");
        }
    }

    private void editProfile(Scanner scanner) {
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
            if (deleteUser.deleteUser(currentUser)) {
                System.out.println("Аккаунт удален успешно");
                currentUser = null;
                start();
            } else {
                System.out.println("Ошибка удаления аккаунта.");
            }
        }
    }

    private void generateReport(String email) {
        generateHabitReport.execute(email);
    }

    private void editHabit(Scanner scanner) {
        System.out.println("Введите название привычки, которую хотите изменить: ");
        String oldTitle = scanner.nextLine();

        System.out.println("Введите новое название привычки: ");
        String newTitle = scanner.nextLine();

        System.out.println("Введите новое описание привычки: ");
        String newDescription = scanner.nextLine();

        System.out.println("Как часто вы будете ее выполнять(ежедневно / еженедельно): ");
        String newFrequency = scanner.nextLine();

        if (updateHabit.execute(currentUser, oldTitle, newTitle, newDescription, newFrequency)) {
            System.out.println("Привычка изменнена");
        } else {
            System.out.println("Не удалось найти привычку с таким названием.");
        }
    }

    private void deleteHabit(Scanner scanner) {
        System.out.println("Введите название привычки для удаления: ");
        String title = scanner.nextLine();

        if(deleteHabit.execute(currentUser, title)) {
            System.out.println("Привычка '" + title + "' успешно удалена!");
        } else {
            System.out.println("Не удалось удалить привычку!");
        }
    }

    private void markHabit(Scanner scanner) {
        System.out.println("Введите название привычки для отметки выполнения: ");
        String title = scanner.nextLine();

        if (markHabitAsCompleted.execute(currentUser, title)) {
            System.out.println("Привычка '" + title + "' успешно отмеченная как выполнена!");
        } else {
            System.out.println("Не удалось отметить привычку!");
        }
    }

    private void viewHabitDateStatistics(Scanner scanner) {
        System.out.println("Введите название привычки для просмотра статистики: ");
        String title = scanner.nextLine();

        System.out.println("Введите дату начала (ГГГГ-ММ-ДД)");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());

        System.out.println("Введите дату окончания (ГГГГ-ММ-ДД)");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        int completedCount = getHabitStatistic.getCompletedCount(currentUser, title, startDate, endDate);
    }
}
