import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Home from 'Components/Pages/Home/Home';
import Box from '@mui/material/Box';
import SignUp from 'Components/Pages/SignUp/SignUp';
import styled from '@emotion/styled';
import { Grid } from '@mui/material/';
import Login from '../../Pages/Login/Login';

export default function LoginLayout() {
  return (
    <div>
      <Box sx={{ paddingLeft: 35 }}>
        <BrowserRouter>
          <Switch>
            <Route path="/">
              <Login />
              <SignUp />
            </Route>
          </Switch>
        </BrowserRouter>
      </Box>
    </div>
  );
}
