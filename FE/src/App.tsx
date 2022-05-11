import React, { Suspense, lazy } from 'react';
import { Switch, Route } from 'react-router-dom';
import CssBaseline from '@mui/material/CssBaseline';

import { ThemeProvider, createTheme } from '@mui/material/styles';

const LoginPage = lazy(() => import('Components/Pages/Login/Login'));
const SignUpPage = lazy(() => import('Components/Pages/SignUp/SignUp'));
const HomeLayoutPage = lazy(
  () => import('Components/Layouts/BasicLayout/BasicLayout'),
);

// eslint-disable-next-line @typescript-eslint/no-empty-function
const ColorModeContext = React.createContext({ toggleColorMode: () => {} });

function App() {
  const [mode, setMode] = React.useState<'light' | 'dark'>('light');
  const colorMode = React.useMemo(
    () => ({
      toggleColorMode: () => {
        setMode((prevMode) => (prevMode === 'light' ? 'dark' : 'light'));
      },
    }),
    [],
  );

  const theme = React.useMemo(
    () =>
      createTheme({
        palette: {
          mode,
        },
      }),
    [mode],
  );

  return (
    <ColorModeContext.Provider value={colorMode}>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <Suspense fallback={<div>로딩 중...</div>}>
          <Switch>
            <Route path="/signup" component={SignUpPage} exact />
            <Route path="/login" component={LoginPage} exact />
            <Route path="/" component={HomeLayoutPage} />
          </Switch>
        </Suspense>
      </ThemeProvider>
    </ColorModeContext.Provider>
  );
}

export default App;
