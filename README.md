# NeoBinding
Android View Model binding implement, base on RxJava

## Usage

LoginActivity

    val viewCreator = ViewCreator(ViewModelBinder(bindingContext, { LoginViewModel() }, { LoginBindableView(CommandHandler({ e -> loginFailed(e) }, { user -> loginSuccess(user) })) }), R.layout.activity_login)
    setContentView(viewCreator.view(this, null))

LoginBindableView

    class LoginBindableView(val loginHandler: CommandHandler<Throwable, User>) : BindableView() {

    override fun inject(bindingContext: BindingContext<*>, view: View) {
        val etLogin = view.findViewById(R.id.et_login) as EditText
        val etPassword = view.findViewById(R.id.et_password) as EditText
        val btnLogin = view.findViewById(R.id.btn_login)

        addTwoWayPropertyBinding(TwoWayPropertyBinding<String, String>(LoginViewModel.PROPERTY_NAME, etLogin.textChanges().map { it.toString() }, etLogin.text()))
        addTwoWayPropertyBinding(TwoWayPropertyBinding<String, String>(LoginViewModel.PROPERTY_PASSWORD, etPassword.textChanges().map { it.toString() }, etPassword.text()))
        addMultiplePropertyBinding(OneWayPropertyBinding<Boolean, String>(listOf(LoginViewModel.PROPERTY_NAME, LoginViewModel.Companion.PROPERTY_PASSWORD), btnLogin.enabled(), ArrayToBooleanConverter()))
        addCommandBinding(CommandBinding(LoginViewModel.COMMAND_LOGIN, btnLogin.clicks(), loginHandler, btnLogin.enabled()))
    }
    }
    
LoginViewModel

    class LoginViewModel : BindableModel<User>() {
    companion object {
        public val PROPERTY_NAME = "property_login_name"
        public val PROPERTY_PASSWORD = "property_login_password"
        public val COMMAND_LOGIN = "command_login"
    }

    override fun notifyPropertyChange(t: User?) {
    }

    override fun initProperty() {
        addProperty(PROPERTY_NAME, Property("xxxxxx@xxx.com"))
        addProperty(PROPERTY_PASSWORD, Property("xxxxxxxxxx"))
    }

    override fun initCommand() {
        addCommand(COMMAND_LOGIN, Command<User>(Observable.defer { this.observableForLogin() }))
    }

    private fun ensureInputValid() {
        val name = property<String>(PROPERTY_NAME).value
        val password = property<String>(PROPERTY_PASSWORD).value

        if (name.isNullOrEmpty() || password.isNullOrEmpty()) throw RuntimeException(if (name.isNullOrEmpty()) "用户名不能为空" else "密码不能为空")
    }

    private fun observableForLogin(): Observable<User> {
        ensureInputValid()
        return CaishuoService.getInstance().login(property<String>(PROPERTY_NAME).value, property<String>(PROPERTY_PASSWORD).value)
    }
    }
    
    
