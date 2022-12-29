package com.example.astontraining.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.astontraining.R
import com.example.astontraining.databinding.ActivityMainBinding

/**
 * TODO: Finish tablet view
 * TODO: Add a contact search
 * TODO: Add Item Decorator separator and padding
 * TODO: Add picture editing by choosing a picture id
 * TODO: Format a phone number in the EditText
 * TODO: Create an initial database
 * TODO: Make comments and delete unnecessary code

Задание 2
Необходимо реализовать приложение для отображения списка контактов и деталей контакта.
Приложение должно состоять из 1 Activity и 2 Fragment.
На первом фрагменте будет изображен список контактов, на втором детали контакта.
Сам список должен быть представлен в виде LinearLayout или же с помощью похожего контейнера.
Количество контактов может быть небольшим: 3-4 штуки.
Каждый элемент из списка должен содержать: Имя, Фамилия, Номер телефона.
При нажатии на какой-то элемент из списка, необходимо открыть второй фрагмент
и отобразить там данные того элемента, по которому юзер нажал на прошлом экране.
При нажатии системной кнопки назад, необходимо возвращать на предыдущий экран со списком контактов.
Если пользователь нажимает на кнопку назад и при этом находится на списке контактов,
то приложение необходимо закрывать.

Усложнение 1
Необходимо добавить возможность редактирования контакта на экране деталей контакта.
При нажатии на кнопку сохранить, необходимо вернуться на предыдущий экран со списком.
После сохранения, изменения так же должны быть видны в списке контактов.

Усложнение 2
Необходимо добавить специальный режим для планшетов.
Если приложение запускается на широком экране, то необходимо переключаться в иной режим работы.
При запуске пользователь так же должен видеть фрагмент со списком контактов.
При клика по какому-то элементу необходимо так же отобразить детали контакта,
но уже рядом со списком контактов. Это должны быть 2 отдельных фрагмента.

Продолжаем развивать маленькое приложение по контактам из предыдущего занятия.
Необходимо реализовать список, сделанный на стандартном контейнере, с помощью RecyclerView.
Количество элементов в списке должно быть более 100.
К информации по контакту необходимо добавить еще и картинку.
Картинки можно брать с ресурса https://picsum.photos/,
важно, чтобы у каждого контакта картинки были разные.
Логику с переходом на детали необходимо сохранить.

Усложнение
Для экрана со списком необходимо добавить возможность поиска по фамилии и имени.
Так же необходимо добавить удаление контакта из списка.
С помощью длинного нажатия на элемент,
необходимо отобразить AlertDialog или же PopUp с опцией удаления.
При нажатии на удалить, контакт должен пропадать из списка.

Дополнительное усложнение
Все изменения данных в адаптере необходимо проводить с помощью DiffUtil.

Дополнительное задание
Необходимо добавить разделитель между элементами и отступы, с помощью ItemDecoration.
 */

/**
 * A Main activity that hosts all fragments for this application and hosts the nav controller.
 */
class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve NavController from the NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set up the action bar for use with the NavController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    /**
     * Handle navigation when the user chooses Up from the action bar.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}