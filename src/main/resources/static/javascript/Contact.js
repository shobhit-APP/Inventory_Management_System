function showSuccess(event) {
        document.getElementById('successMessage').style.display = 'block';
        document.querySelector('form').reset();

        // Scroll to success message
        document.getElementById('successMessage').scrollIntoView({
            behavior: 'smooth',
            block: 'nearest'
        });
    }

    function toggleTheme() {
        const body = document.body;
        const themeBtn = document.querySelector('.theme-toggle');

        if (body.classList.contains('dark-mode')) {
            body.classList.remove('dark-mode');
            body.classList.add('light-mode');
            themeBtn.textContent = '🌙';
        } else {
            body.classList.remove('light-mode');
            body.classList.add('dark-mode');
            themeBtn.textContent = '🌞';
        }
    }

    // Initialize theme
    document.addEventListener('DOMContentLoaded', function() {
        document.body.classList.add('light-mode');

        const inputs = document.querySelectorAll('input, select, textarea');

        inputs.forEach(input => {
            input.addEventListener('blur', function() {
                if (this.required && !this.value.trim()) {
                    this.style.borderColor = '#e74c3c';
                } else if (this.value.trim()) {
                    this.style.borderColor = '#27ae60';
                }
            });

            input.addEventListener('focus', function() {
                this.style.borderColor = '#3498db';
            });
        });
    });