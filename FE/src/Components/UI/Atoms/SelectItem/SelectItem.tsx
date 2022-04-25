import React from 'react';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Box from '@mui/material/Box';
import Select, { SelectChangeEvent } from '@mui/material/Select';

interface selectProps {
  label: string;
  Items: string[];
  change?: (event: string) => void;
}

export default function SelectItem({ label, Items, change }: selectProps) {
  const [value, setValue] = React.useState('');

  const handleChange = (event: SelectChangeEvent) => {
    setValue(event.target.value);
    if (typeof change === 'function') change(event.target.value);
  };

  return (
    <Box>
      <FormControl sx={{ my: 1, minWidth: 120 }} size="small">
        <InputLabel id="demo-simple-select-autowidth-label">{label}</InputLabel>
        <Select
          labelId="demo-simple-select-autowidth-label"
          id="demo-simple-select-autowidth"
          value={value}
          onChange={handleChange}
          autoWidth
          label={label}
        >
          <MenuItem value="">
            <em>None</em>
          </MenuItem>
          {Items.map((value) => (
            <MenuItem key={value} value={value}>
              {value}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </Box>
  );
}

SelectItem.defaultProps = {
  change: undefined,
};
