async function saveCustomer() {
    const payload = {
        customer: {
            username: document.getElementById("username").value,
            dob: document.getElementById("dob").value,
            mobile: document.getElementById("mobile").value,
        },
        otp: document.getElementById("otp").value,
        email: document.getElementById("otp-email").value,
    };

    try {
        const response = await fetch('/admin/saveCustomer', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload),
        });

        const result = await response.json();
        if (!response.ok) {
            alert("Validation Failed: " + JSON.stringify(result));
        } else {
            alert(result);
        }
    } catch (error) {
        console.error('Unexpected error', error);
    }
}
