import { useState } from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LoginIcon from '@mui/icons-material/Login';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { Link } from 'react-router-dom';
import styled from '@emotion/styled';
import { fileApi } from '../../../api/index';

interface Message {
  message: string;
  status: string;
}

function Copyright(props: any) {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      // eslint-disable-next-line react/jsx-props-no-spreading
      {...props}
    />
  );
}

const theme = createTheme();

function Login() {
  const [loginData, setLoginData] = useState<string>('');

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    console.log(event.currentTarget);
    const data = new FormData(event.currentTarget);
    const loginData = JSON.stringify({
      principal: data.get('id'),
      credential: data.get('password'),
    });
    console.log(loginData);
    // const principa = data.get('id');
    // const credential = data.get('password');

    // setLoginData(data);
    fileApi.post(`user/auth/signin`, loginData).then((res) => {
      const datas = res.data as Message;
      console.log(datas);
    });
  };

  // const loginAPI = (data: FormData) => {
  //   console.log(data);
  //   window.location.href = '/';
  // };

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'primary.main' }}>
            <LoginIcon />
          </Avatar>
          <Typography component="h1" variant="h3">
            Dockerby
          </Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              InputLabelProps={{ required: false }}
              required
              fullWidth
              id="id"
              label="아이디"
              name="id"
              autoComplete="id"
              autoFocus
            />
            <TextField
              margin="normal"
              InputLabelProps={{ required: false }}
              required
              fullWidth
              name="password"
              label="패스워드"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            {/* <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="Remember me"
            /> */}
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              // onClick={loginAPI(loginData)}
            >
              로그인
            </Button>
            <Grid container justifyContent="flex-end">
              {/* <Grid item xs>
                <Link href="#!" variant="body2">
                  Forgot password?
                </Link>
              </Grid> */}
              <Grid item direction="row-reverse">
                <Link to="/signup" style={{ textDecoration: 'none' }}>
                  <span>가입하기</span>
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Copyright sx={{ mt: 8, mb: 4 }} />
      </Container>
    </ThemeProvider>
  );
}
export default Login;
