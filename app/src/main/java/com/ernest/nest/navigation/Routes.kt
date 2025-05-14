package com.ernest.nest.navigation


const val ROUT_HOME = "home"



//Authentication
const val ROUT_REGISTER = "Register"
const val ROUT_ACCOUNTS = "accounts"
const val ROUT_BUDGET = "budget"
const val ROUT_REPORTS = "reports"
const val ROUT_LOGIN = "Login"
const val ROUT_SAVINGS= "savings"
const val ROUT_SETTINGS = "settings"
const val ROUT_ADD_TRANSACTION = "addtransaction"
const val ROUT_HELP = "help"
const val ROUT_EDIT_TRANSACTION = "edittransaction"




const val ROUT_SPLASH = "splash"

// Savings

const val ROUT_ADD_SAVINGS = "add_savings"

const val ROUT_EDIT_SAVINGS = "edit_savings/{savingsId}"

// ✅ Helper function for navigation
fun editSavingsRoute(savingsId: Int) = "edit_savings/$savingsId"

// Budget

const val ROUT_ADD_BUDGET = "add_budget"

const val ROUT_EDIT_BUDGET = "edit_budget/{budgetId}"

// ✅ Helper function for navigation
fun editBudgetRoute(budgetId: Int) = "edit_budget/$budgetId"


// Accounts

const val ROUT_ADD_ACCOUNT = "add_account"

const val ROUT_EDIT_ACCOUNT = "edit_account/{accountId}"

// ✅ Helper function for navigation
fun editAccountRoute(accountId: Int) = "edit_account/$accountId"

