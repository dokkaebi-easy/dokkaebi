import React from 'react';
import { DefaultTheme, ThemeProvider } from 'styled-components';

type ColorProviderProps = {
  children: React.ReactNode;
  theme: DefaultTheme;
};

function ColorProvider({ children, theme }: ColorProviderProps) {
  return <ThemeProvider theme={theme}>{children}</ThemeProvider>;
}

export default ColorProvider;
