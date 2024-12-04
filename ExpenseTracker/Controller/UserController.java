package com.example.ExpenseTracker.Controller;

import com.example.ExpenseTracker.Modal.Category;
import com.example.ExpenseTracker.Modal.Transaction;
import com.example.ExpenseTracker.Service.CategoryService;
import com.example.ExpenseTracker.Service.TransactionService;
import com.example.ExpenseTracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    // Dashboard for logged-in user
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Long userId = getLoggedInUserId(); // Replace this with actual authentication logic
        List<Transaction> transactions = transactionService.getTransactionsByUser(userId);
        List<Category> categories = categoryService.getAllCategories();

        double totalIncome = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Income"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpense = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Expense"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", categories);
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpense", totalExpense);
        return "user/dashboard";
    }

    // Add Transaction
    @GetMapping("/transaction/add")
    public String addTransactionForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "user/addTransaction";
    }

    @PostMapping("/transaction/add")
    public String addTransaction(@ModelAttribute Transaction transaction) {
        transaction.setUser(userService.findUserById(getLoggedInUserId()).orElse(null)); // Replace with real auth
        transactionService.saveTransaction(transaction);
        return "redirect:/user/dashboard";
    }

    // Add Category
    @GetMapping("/category/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "user/addCategory";
    }

    @PostMapping("/category/add")
    public String addCategory(@ModelAttribute Category category) {
        category.setUser(userService.findUserById(getLoggedInUserId()).orElse(null)); // Replace with real auth
        categoryService.saveCategory(category);
        return "redirect:/user/dashboard";
    }

    private Long getLoggedInUserId() {
        // Mock function - Replace with real authentication logic to fetch logged-in user ID
        return 1L;
    }
}

