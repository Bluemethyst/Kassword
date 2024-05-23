window.onload = function () {
    console.log("%c HEY STOP SNOOPING FOR AN API! use https://password.ninja/api instead! Its what this is built on!", "color: red; font-size: 20px; font-weight: bold; text-align: center;");
    const submitForm = function (event) {
        event.preventDefault();
        let formData = new FormData(this);
        let object = {};
        formData.forEach(function (value, key) {
            object[key] = value;
        });
        console.log(object)
        fetch('/getPasswords', {
            method: 'POST',
            body: JSON.stringify(object),
        })
            .then(response => response.json())
            .then(data => {
                console.log(JSON.parse(data))
                let parsedData = JSON.parse(data);
                document.querySelector('.response p').innerText = parsedData.join(', ');            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };
    document.getElementById('passwordForm').addEventListener('submit', submitForm);
    submitForm.call(document.getElementById('passwordForm'), new Event('submit'));

    function downloadPasswords() {
        let passwords = document.querySelector('.response p').innerText;
        passwords = passwords.includes(' ') ? passwords.split(', ') : passwords.split(',');
        fetch('/downloadPasswords', {
            method: 'POST',
            body: JSON.stringify({passwords: passwords})
        })
            .then(response => response.blob())
            .then(blob => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'passwords.csv';
                a.click();
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }
    document.getElementById('download').addEventListener('click', function(event) {
        event.preventDefault();
        downloadPasswords();
    });
}