import React, { useEffect } from 'react';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Box from '@mui/material/Box';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import { v4 as uuid } from 'uuid';

interface selectProps {
  label: string;
  Items: string[];
  defaultValue?: string;
  Change?: (event: string) => void;
  Click?: (event: number) => void;
}

export default function SelectItem({
  label,
  Items,
  defaultValue = '',
  Change,
  Click,
}: selectProps) {
  const [value, setValue] = React.useState(defaultValue);

  const handleChange = (event: SelectChangeEvent) => {
    setValue(event.target.value);
    if (typeof Change === 'function') Change(event.target.value);
  };

  const handleItemClick = (index: number) => {
    if (typeof Click === 'function') Click(index);
  };

  useEffect(() => {
    setValue(defaultValue);
  }, [defaultValue]);

  return (
    <Box>
      <FormControl sx={{ my: 1, minWidth: 120 }} fullWidth size="small">
        <InputLabel id="demo-simple-select-autowidth-label">{label}</InputLabel>
        <Select
          labelId="demo-simple-select-autowidth-label"
          id="demo-simple-select-autowidth"
          value={value}
          onChange={handleChange}
          label={label}
        >
          <MenuItem value="">
            <em>None</em>
          </MenuItem>
          {Items.map((value, index) => (
            <MenuItem
              key={uuid()}
              value={value}
              onClick={() => handleItemClick(index)}
            >
              {value}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </Box>
  );
}

SelectItem.defaultProps = {
  defaultValue: '',
  Change: undefined,
  Click: undefined,
};
