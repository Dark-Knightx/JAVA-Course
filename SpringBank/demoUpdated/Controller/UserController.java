package com.example.ExpenseTracker.Controller;

import com.example.ExpenseTracker.Modal.Category;
import com.example.ExpenseTracker.Modal.Transaction;
import com.example.ExpenseTracker.Modal.User;
import com.example.ExpenseTracker.Service.CategoryService;
import com.example.ExpenseTracker.Service.TransactionService;
import com.example.ExpenseTracker.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

    // Dashboard for the logged-in user
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Long userId = getLoggedInUserId(); // Fetch the logged-in user's ID
        if (userId == null) {
            return "redirect:/loginPage"; // Redirect to login if user is not authenticated
        }

        // Fetch user-specific transactions and categories
        List<Transaction> transactions = transactionService.getTransactionsByUser(userId);
        List<Category> categories = categoryService.getCategoriesByUser(userId);

        // Calculate total income and expenses
        double totalIncome = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Income"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpense = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Expense"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        // Add attributes to the model for Thymeleaf to render
        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", categories);
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpense", totalExpense);

        return "user/dashboard"; // Return the user dashboard view
    }

    // Show form to add a new transaction
    @GetMapping("/transaction/add")
    public String addTransactionForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("categories", categoryService.getCategoriesByUser(getLoggedInUserId()));
        return "user/addTransaction"; // Return add transaction view
    }

    // Process the form to add a new transaction
    @PostMapping("/transaction/add")
    public String addTransaction(@ModelAttribute Transaction transaction) {
        Long userId = getLoggedInUserId();
        if (userId == null) {
            return "redirect:/loginPage";
        }

        // Safely retrieve the user from Optional and set it to the transaction
        transaction.setUser(userService.findUserById(userId).orElse(null));

        transactionService.saveTransaction(transaction);
        return "redirect:/user/dashboard"; // Redirect to dashboard after saving
    }


    // Show form to add a new category
    @GetMapping("/category/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "user/addCategory"; // Return add category view
    }

    // Process the form to add a new category
    @PostMapping("/category/add")
    public String addCategory(@ModelAttribute Category category) {
        Long userId = getLoggedInUserId();
        if (userId == null) {
            return "redirect:/loginPage";
        }

        category.setUser(userService.findUserById(userId).orElse(null));
        categoryService.saveCategory(category);
        return "redirect:/user/dashboard"; // Redirect to dashboard after saving
    }

    /**
     * Fetch the currently logged-in user's ID using Spring Security.
     *
     * @return User ID or null if not authenticated.
     */
    private Long getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getPrincipal().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();
            return user.getId();
        }
        return null;
    }
}
